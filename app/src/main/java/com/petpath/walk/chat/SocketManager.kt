package com.petpath.walk.chat

import android.annotation.SuppressLint
import androidx.navigation.NavController
import com.petpath.walk.data.ChatMessage
import com.petpath.walk.viewModel.ChatViewModel
import io.socket.client.IO
import io.socket.client.Socket
import org.json.JSONObject
import java.lang.ref.WeakReference
import java.net.URISyntaxException

@SuppressLint("StaticFieldLeak")
object SocketManager {

    private lateinit var mSocket: Socket
    private const val authToken =
        "eyJ1aWQiOjQsInR5cGUiOiJvd25lciIsInByb2ZpbGVJbWciOiI0LTFkOGE5MCIsIl90b2tlbkNyZWF0ZUF0IjoxNzIzMzc4NjQ1NDMyfQ==.e9af902a6fda4de4374ee8bc7393def746bd873b88efc7f6e657cdfd1e0788fb"
    var navController: NavController? = null
    // Weak reference to avoid memory leaks
    private var chatViewModelRef: WeakReference<ChatViewModel>? = null
    private var chatRoomNumber : Int? =0
    fun setChatViewModel(chatViewModel: ChatViewModel) {
        chatViewModelRef = WeakReference(chatViewModel)
    }

    fun connectSocket() {
        if (SocketManager::mSocket.isInitialized && mSocket.connected()) {
            println("Socket is already connected")
            return
        }
        val currentRoute = navController?.currentBackStackEntry?.destination?.route
        if (currentRoute != null) {
            println("현재 화면: $currentRoute")
        }
        try {
            val opts = IO.Options()
            opts.path = "/socket"
            opts.transports = arrayOf("websocket")
            opts.extraHeaders = mapOf("auth" to listOf(authToken))

            mSocket = IO.socket("https://petpath.ritchko.com", opts)

            // Socket connection listeners
            mSocket.on(Socket.EVENT_CONNECT) {
                println("Connected to socket")
            }

            mSocket.on(Socket.EVENT_DISCONNECT) {
                println("Disconnected from socket")
            }
            // 에러 리스너 등록
            mSocket.on(Socket.EVENT_CONNECT_ERROR) { args ->
                println("error from socket")
                handleError(args)
            }
            // 리스너 등록
            mSocket.on("RecieveChatEvent") { args ->
                println("RecieveChatEvent")
                val message = args[0] as JSONObject
                handleReceiveChatEvent(message)
            }

            mSocket.on("ChatReadEvent") { args ->
                println("ChatReadEvent")
                val event = args[0] as JSONObject
                handleChatReadEvent(event)
            }

            // 소켓 연결 시작
            mSocket.connect()

        } catch (e: URISyntaxException) {
            e.printStackTrace()
        }
    }

    fun setChatRoom(chatId: Int) {
        if (!SocketManager::mSocket.isInitialized || !mSocket.connected()) {
            println("소켓 연결 x")
            return
        }
        val data = JSONObject()
        data.put("chatId", chatId)
        chatRoomNumber = chatId
        println("enterChatRoom")
        mSocket.emit("SetChatRoomMessage", data)
    }

    fun chatReadMessage(chatIndex : Int){
        if (!SocketManager::mSocket.isInitialized || !mSocket.connected()) {
            println("소켓 연결 x")
            return
        }
        val data = JSONObject()
        data.put("chatIndex", chatIndex)
        println("ChatReadMessage")
        mSocket.emit("ChatReadMessage",data)
    }

    private fun handleReceiveChatEvent(message: JSONObject) {
        println("Received message: $message")


        val index = message.getInt("index")
        val sender = message.getInt("sender")
        val type = message.getString("type")
        val content = message.getString("content")
        val createAt = message.getString("createAt")


        val chatMessage = ChatMessage(
            chatIndex = index.toLong(),
            sender = sender.toLong(),
            type = type,
            content = content,
            createAt = createAt
        )
        //톡방화면에 있으면 바로 읽음 처리
        val currentRoute = navController?.currentBackStackEntry?.destination?.route
        val chatRoomId = navController?.currentBackStackEntry?.arguments?.getString("chatId")
        if (currentRoute == "chatHistory/{chatId}" && chatRoomId == chatRoomNumber.toString()) {
            chatViewModelRef?.get()?.receiveMessage(chatMessage)
            mSocket.emit("ChatReadMessage", JSONObject().apply {
                put("chatIndex", index)
            })
        }
        else{
            //톡방화면에 없으면 알림 처리

            print("성공?")
        }

    }

    private fun handleChatReadEvent(event: JSONObject) {
        val senderId = event.getInt("sender")
        val chatIndex = event.getInt("index")
        chatViewModelRef?.get()?.markAsRead(chatIndex)
    }

    private fun handleError(args: Array<Any>) {
        if (args.isNotEmpty()) {
            val errorResponse = args[0] as? JSONObject
            errorResponse?.let { json ->
                val responseCode = json.getInt("response")
                val message = json.getString("message")
                val errorCode = json.optString("errorCode")
                val target = json.optString("target")

                when (errorCode) {
                    "Unauthorized" -> {
                        println("Error: Unauthorized - $message")
                        // 로그인 페이지로 이동 또는 재인증 요청 로직
                    }
                    "InputValueNotValid" -> {
                        println("Error: InputValueNotValid - $message, Target: $target")
                        // 입력값 유효성 확인 로직
                    }
                    "JoinedChatNotFound" -> {
                        println("Error: JoinedChatNotFound - $message, Target: $target")
                        // 채팅방 재설정 로직
                    }
                    else -> {
                        println("Error: $message (Code: $responseCode, ErrorCode: $errorCode, Target: $target)")
                        // 기타 에러 처리 로직
                    }
                }
            } ?: run {
                println("Unknown error: ${args.joinToString()}")
            }
        }
    }

    private fun getUserId(): Int {
        // 현재 사용자 ID를 반환하는 로직 구현
        return 4
    }

    fun sendTextMessage(isText : Boolean, message: Any) {
        if (!SocketManager::mSocket.isInitialized || !mSocket.connected()) {
            println("소켓 연결 x")
            return
        }
        if(isText){//1 은 문자열
            val data = JSONObject()
            data.put("type", "text")
            data.put("content", message as String)
            println("SendChatTextMessage")
            mSocket.emit("SendChatMessage", data)
        }
        else{
            val data = JSONObject()
            data.put("type", "image")
            data.put("content", message as String)
            println("SendChatImageMessage")
            mSocket.emit("SendChatMessage", data)
        }

    }

}
