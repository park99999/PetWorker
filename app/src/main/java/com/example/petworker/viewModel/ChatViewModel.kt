package com.example.petworker.viewModel

import android.content.Context
import android.database.Cursor
import android.net.Uri
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.petworker.ApiInterface
import com.example.petworker.ChatHistoryDatabase
import com.example.petworker.UserManager
import com.example.petworker.chat.SocketManager
import com.example.petworker.chat.data.ChatHistory
import com.example.petworker.data.ApiRequest
import com.example.petworker.data.ChatHistoryResponse
import com.example.petworker.data.ChatMessage
import com.example.petworker.data.FileConverter
import com.example.petworker.data.FileUploadResponse
import com.example.petworker.data.UnreadChatIndexData
import com.example.petworker.data.UnreadChatIndexResponse
import com.example.petworker.getRetrofit
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ChatViewModelFactory : ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ChatViewModel::class.java)) {
            return ChatViewModel() as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
class ChatViewModel:ViewModel() {
    private val authToken = UserManager.userToken!!

    private val _chatLastIndex = MutableStateFlow(UnreadChatIndexData())
    val chatLastIndex: StateFlow<UnreadChatIndexData> = _chatLastIndex.asStateFlow()
    private val _chatHistoryList = MutableStateFlow<List<ChatMessage>>(emptyList())
    val chatHistoryList: StateFlow<List<ChatMessage>> = _chatHistoryList.asStateFlow()

    var chatIndex : Int? = 0
    private val service: ApiInterface = getRetrofit().create(ApiInterface::class.java)
    fun getChatHistory(chatId: Long,context: Context) {
        val unreadChatIndexRequest = ApiRequest(
            operation = "GetUnreadChatIndex",
            param = mapOf("chatId" to chatId)
        )
        val db = ChatHistoryDatabase.getInstance(context)
        CoroutineScope(Dispatchers.IO).launch {
            chatIndex = db!!.chatHistoryDao().getChatLastIndex(chatId)
        }
        //chatHistory APi 호출
        val callUnreadChatIndex = service.getUnreadChatIndex(authToken, listOf(unreadChatIndexRequest))
        callUnreadChatIndex.enqueue(object : Callback<List<UnreadChatIndexResponse>> {
            override fun onResponse(call: Call<List<UnreadChatIndexResponse>>, response: Response<List<UnreadChatIndexResponse>>) {
                if (response.isSuccessful) {
                    val unreadChatIndexResponse =
                        response.body()?.get(0)
                    val cursor = unreadChatIndexResponse?.data?.lastChatIndex
                    val indexData = unreadChatIndexResponse?.data
                    if(indexData != null){
                        _chatLastIndex.apply {
                            value = indexData
                        }
                    }
                    if (cursor != null && cursor != chatIndex) {
                        val chatHistoryRequest = ApiRequest(
                            operation = "GetChatHistory",
                            param = mapOf("chatId" to chatId, "cursor" to cursor)
                        )
                        val callChatHistory = service.getChatHistory(authToken, listOf(chatHistoryRequest))
                        callChatHistory.enqueue(object : Callback<List<ChatHistoryResponse>> {
                            override fun onResponse(
                                call: Call<List<ChatHistoryResponse>>,
                                response: Response<List<ChatHistoryResponse>>
                            ) {
                                if (response.isSuccessful) {
                                    val chatHistoryResponse =
                                        response.body()?.get(0)
                                    val charHistory = chatHistoryResponse?.data
                                    // 채팅 내역 처리
                                    if (charHistory != null) {
                                        _chatHistoryList.update { currentList ->
                                            currentList + charHistory
                                        }
                                        for (chat in charHistory){
                                            val newChat = ChatHistory(
                                                chatRoomId = chatId,
                                                chatIndex = chat.chatIndex!!,
                                                sender = chat.sender!!,
                                                type = chat.type!!,
                                                content = chat.content!!,
                                                createAt = chat.createAt!!
                                            )
                                            CoroutineScope(Dispatchers.IO).launch {
                                                db!!.chatHistoryDao().insertItem(newChat)
                                            }
                                        }
                                    }

                                    //읽음 처리
                                    SocketManager.chatReadMessage(cursor)
                                } else {
                                    // RoomDb에서 채팅들 끄내오기
                                }
                            }

                            override fun onFailure(call: Call<List<ChatHistoryResponse>>, t: Throwable) {
                                // 네트워크 오류 처리
                            }
                        })
                    } else {
                        CoroutineScope(Dispatchers.IO).launch {
                            print(db!!.chatHistoryDao().getAllChatHistory(chatId).toString())
                        }
                    }
                } else {
                    // GetUnreadChatIndex API 오류 처리
                }
            }
            override fun onFailure(call: Call<List<UnreadChatIndexResponse>>, t: Throwable) {
                // 네트워크 오류 처리
            }
        })
    }
    fun joinRoom(chatId: Int) {
        _chatHistoryList.update { emptyList() }
//        SocketManager.apply {
//            createChatRoom(chatId)
//        }
    }

    fun sendMessage(message: Any, isText: Boolean) {
        SocketManager.sendTextMessage( isText,message)
    }

    fun receiveMessage(message: ChatMessage) {
        _chatHistoryList.update { listOf(message) + it }
        _chatHistoryList.value.forEach { chatMessage ->
            println("Message: ${chatMessage.content}")
        }
    }

    fun getImgTokenAndSendImg(uris: List<*>,context: Context) {

        for(uri in uris){
            val filePart = prepareFilePart("file", uri as Uri, context)
            val call = service.uploadImage(filePart!!)
            call.enqueue(object : Callback<FileUploadResponse> {
                override fun onResponse(call: Call<FileUploadResponse>, response: Response<FileUploadResponse>) {
                    if (response.isSuccessful) {
                        // 파일 업로드 성공
                        println("Upload success: ${response.body()?.data}")
                        response.body()?.data?.let { SocketManager.sendTextMessage(false, it) }
                    } else {
                        // 파일 업로드 실패
                        println("Upload failed: ${response.errorBody()?.string()}")
                    }
                }

                override fun onFailure(call: Call<FileUploadResponse>, t: Throwable) {
                    // 네트워크 오류 또는 기타 문제로 인해 업로드 실패
                    println("Upload error: ${t.message}")
                }
            })
        }
    }
    private fun prepareFilePart(partName: String, fileUri: Uri, context: Context): MultipartBody.Part? {
        val file =  FileConverter.uriToFile(context,fileUri)
        println("--------------")
        println(fileUri.scheme)
        if (file != null) {
            println(file.absolutePath)
        }
        if (file != null) {
            println(file.canonicalPath)
        }
        println(getRealPathFromUri(context,fileUri))

        // 파일의 MIME 타입 추출
        val requestFile = file
            ?.asRequestBody(context.contentResolver.getType(fileUri)?.toMediaTypeOrNull())

        // MultipartBody.Part 객체 생성

        return requestFile?.let { MultipartBody.Part.createFormData(partName, file.name, it) }

    }
    fun getRealPathFromUri(context: Context, uri: Uri): String? {
        // Android SDK 19 이상인지 확인합니다.
        return if ("content".equals(uri.scheme, ignoreCase = true)) {
            println("content")
            // Content URI인 경우
            getDataColumn(context, uri, null, null)
        } else {
            // File URI인 경우
            uri.path
        }

    }

    private fun getDataColumn(context: Context, uri: Uri?, selection: String?, selectionArgs: Array<String>?): String? {
        var cursor: Cursor? = null
        val column = "_data"
        val projection = arrayOf(column)

        try {
            cursor = uri?.let { context.contentResolver.query(it, projection, selection, selectionArgs, null) }
            if (cursor != null && cursor.moveToFirst()) {
                val columnIndex = cursor.getColumnIndexOrThrow(column)
                return cursor.getString(columnIndex)
            }
        } finally {
            cursor?.close()
        }
        return null
    }

    private fun isExternalStorageDocument(uri: Uri): Boolean {
        return "com.android.externalstorage.documents" == uri.authority
    }

    private fun isDownloadsDocument(uri: Uri): Boolean {
        return "com.android.providers.downloads.documents" == uri.authority
    }

    private fun isMediaDocument(uri: Uri): Boolean {
        return "com.android.providers.media.documents" == uri.authority
    }

}