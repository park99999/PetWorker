package com.example.petworker.chat

import android.annotation.SuppressLint
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.petworker.R
import com.example.petworker.data.ChatMessage
import com.example.petworker.data.ChatRoom
import com.example.petworker.data.UnreadChatIndexData
import com.example.petworker.viewModel.ChatViewModel


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainScreen(navController: NavController) {

            Button(
                onClick = {
                    navController.navigate("chatRooms")
                },
                modifier = Modifier.padding(16.dp)
            ) {
                Text("Go to Chat Rooms")
            }


}
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatRoomsScreen(navController: NavController, chatRooms: List<ChatRoom>) {
    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Chat Rooms") })
        },
        content = {
            LazyColumn {
                items(chatRooms) { chatRoom ->
                    ChatRoomItem(chatRoom) {
                        navController.navigate("chatHistory/${chatRoom.chatRoomId}")
                    }
                }
            }
        }
    )
}

@Composable
fun ChatRoomItem(chatRoom: ChatRoom, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onClick() }
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = "Room Name: ${chatRoom.chatRoomTitle}")
            Text(text = "Last Message: ${chatRoom.chatRoomLastChat}")
        }
    }
}
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatHistoryScreen(chatViewModel: ChatViewModel, chatId: Int) {
    val chatHistory by chatViewModel.chatHistoryList.collectAsState()
    val unreadChatIndexData by chatViewModel.chatLastIndex.collectAsState()

    LaunchedEffect(chatId) {
        chatViewModel.joinRoom(chatId)
        chatViewModel.getChatHistory(chatId)
    }
    Column(modifier = Modifier.fillMaxSize(), ) {
        Row(modifier = Modifier.padding(horizontal = 16.dp, vertical = 10.dp), verticalAlignment = Alignment.CenterVertically) {
            Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "back")
            Text(text = "채팅방 이름", fontSize = 16.sp, fontWeight = FontWeight.Bold,modifier = Modifier.padding(start = 8.dp))
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "산책 예정",
                fontSize = 10.sp,
                color = colorResource(id = R.color.chat_subTitle),
                modifier = Modifier
                    .background(
                        color = colorResource(id = R.color.chat_subTitle_bg),
                        shape = RoundedCornerShape(16.dp)
                    )
                    .padding(horizontal = 6.dp, vertical = 2.dp)
            )

        }
        LazyColumn(
            modifier = Modifier.weight(1f),
            reverseLayout = true
        ) {
            items(chatHistory) { message ->
                ChatMessageItem(message, 4, unreadChatIndexData)
            }
        }
        ChatInputField(chatViewModel)
    }

}

fun DrawScope.drawChatBalloon(color: Color, isCurrentUser: Boolean) {
    val tailWidth = 50f
    val tailHeight = 25f

    // Drawing the bubble with a tail
    val path = Path().apply {
        if (isCurrentUser) {
            // Tail on the right side for current user
            moveTo(0f, size.height)
            lineTo(0f, size.height-40f)
            lineTo(size.width, size.height)
            close()
        } else {
            // Tail on the left side for other user
            moveTo(0f, size.height)
            lineTo(size.width, size.height)
            lineTo(size.width, size.height - 40f)
            close()
        }
    }
    drawPath(path = path, color = color)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatMessageItem(
    message: ChatMessage,
    currentUserId: Int,
    unreadChatIndexData: UnreadChatIndexData
) {
    val isCurrentUser = message.sender?.toInt() == currentUserId
    val isRead = isMessageRead(message, unreadChatIndexData)
    val backgroundColor = if (isCurrentUser) {
        colorResource(id = R.color.chat_mine)
    } else {
        colorResource(id = R.color.chat_opponent)
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalArrangement = if (isCurrentUser) Arrangement.End else Arrangement.Start,
        verticalAlignment = Alignment.Bottom
    ) {
        if (!isCurrentUser) {
            Canvas(modifier = Modifier.size(10.dp)) {
                drawChatBalloon(color = backgroundColor, isCurrentUser = false)
            }
        }
        if(isCurrentUser){
            Column(modifier = Modifier.padding(start = 4.dp,top = 8.dp)) {
                Text(
                    text = if (isRead) "읽음" else "안 읽음",
                    style = MaterialTheme.typography.labelSmall,
                    color = Color.Gray,
                    fontSize = 10.sp,
                )
                message.createAt?.let {
                    Text(
                        text = extractTimeFromTimestamp(it),
                        style = MaterialTheme.typography.labelSmall,
                        color = Color.Gray,
                        fontSize = 10.sp,
                    )
                }
            }
        }
        Card(
            modifier = Modifier
                .widthIn(max = 200.dp),
            shape = if(isCurrentUser) RoundedCornerShape(
                topStart = 15.dp,
                topEnd = 15.dp,
                bottomEnd = 0.dp,
                bottomStart = 15.dp
            ) else RoundedCornerShape(
                topStart = 15.dp,
                topEnd = 15.dp,
                bottomEnd = 15.dp,
                bottomStart = 0.dp) ,
            colors = CardDefaults.cardColors(
                containerColor = backgroundColor
            )
        ) {
            Column(modifier = Modifier.padding(8.dp)) {
                if (message.type == "image") {
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(message.content)
                            .crossfade(true)
                            .build(),
                        contentDescription = "Image Message",
                        modifier = Modifier
                            .fillMaxWidth()
                            .heightIn(min = 100.dp, max = 250.dp)
                    )
                } else {
                    message.content?.let {
                        Text(
                            text = it,
                            color = Color.Black,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }

        if (isCurrentUser) {
            Canvas(modifier = Modifier.size(10.dp)) {
                drawChatBalloon(color = backgroundColor, isCurrentUser = true)
            }
        }
        if(!isCurrentUser) {
            Column(modifier = Modifier.padding(start = 4.dp, top = 8.dp)) {
                Text(
                    text = if (isRead) "읽음" else "안 읽음",
                    style = MaterialTheme.typography.labelSmall,
                    color = Color.Gray,
                    fontSize = 10.sp,
                )
                message.createAt?.let {
                    Text(
                        text = extractTimeFromTimestamp(it),
                        style = MaterialTheme.typography.labelSmall,
                        color = Color.Gray,
                        fontSize = 10.sp,
                    )
                }
            }
        }

    }
}


@Composable
fun ChatInputField(chatViewModel: ChatViewModel) {
    var message by remember { mutableStateOf("") }

    val context = LocalContext.current
    var selectedImages by remember { mutableStateOf<List<Uri>>(emptyList()) }
    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickMultipleVisualMedia()
    ) { uris: List<Uri> ->
        // 선택된 이미지 Uri를 selectedImages에 추가
        if (uris.isNotEmpty()) {
            selectedImages = uris
        }
    }

    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                value = message,
                onValueChange = { message = it },
                modifier = Modifier.weight(1f),
                leadingIcon = {
                    Icon(painter = painterResource(id = R.drawable.gallery_img), contentDescription = "photo",Modifier.clickable {
                            // 사진첩을 열기 위해 imagePickerLauncher 호출
                            imagePickerLauncher.launch(
                                PickVisualMediaRequest(
                                    ActivityResultContracts.PickVisualMedia.ImageOnly
                                )
                            )
                    })
                },
                trailingIcon = {
                    if (message.isNotEmpty() || selectedImages.isNotEmpty()) {
                        IconButton(onClick = {
                            if(selectedImages.isNotEmpty()){
                                chatViewModel.getImgTokenAndSendImg(selectedImages,context)
                                //chatViewModel.sendMessage(selectedImages, false, context)
                            }
                            else{
                                chatViewModel.sendMessage(message, true)
                            }
                            message = ""
                            selectedImages = emptyList()
                        }) {
                            Icon(imageVector = Icons.Default.Send, contentDescription = "Send",Modifier.clickable {
                                if(selectedImages.isNotEmpty()){
                                    chatViewModel.getImgTokenAndSendImg(selectedImages,context)
                                    //chatViewModel.sendMessage(selectedImages, false,context)
                                }
                                else{
                                    chatViewModel.sendMessage(message, true)
                                }
                                message = ""
                                selectedImages = emptyList()
                            })
                        }
                    }
                }
            )
        }

        // 선택된 이미지 미리 보기 영역
        if (selectedImages.isNotEmpty()) {
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                items(selectedImages) { uri ->
                    Image(
                        painter = rememberAsyncImagePainter(uri),
                        contentDescription = null,
                        modifier = Modifier
                            .size(64.dp)
                            .padding(4.dp)
                    )
                }
            }
        }
    }
}

fun isMessageRead(message: ChatMessage, unreadChatIndexData: UnreadChatIndexData): Boolean {
    return if (message.chatIndex != null) {
        message.chatIndex <= unreadChatIndexData.lastChatIndex!!
    } else {
        true
    }
}

fun extractTimeFromTimestamp(timestamp: String): String {
    // Split the string using "T" as the delimiter
    val parts = timestamp.split("T")

    // Return the time part, which is the second part of the split result
    return if (parts.size > 1) {
        parts[1].substring(0, 5) // Extract only the time part (HH:MM:SS)
    } else {
        "Invalid timestamp"
    }
}
