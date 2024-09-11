package com.petpath.walk

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.rememberNavController
import com.petpath.walk.chat.SocketManager
import com.petpath.walk.data.ChatMessage
import com.petpath.walk.gps.GPSData
import com.petpath.walk.theme.PetWorkerTheme
import com.petpath.walk.viewModel.ChatViewModel
import com.petpath.walk.viewModel.ChatViewModelFactory
import com.petpath.walk.viewModel.UserViewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PetWorkerTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    SocketManager.connectSocket()
////                    val viewModelFactory = UserViewModelFactory()
////                    val viewModel = ViewModelProvider(this,viewModelFactory)[UserViewModel::class.java]
////                    val context = LocalContext.current
////                    Greeting(context,viewModel)
//                    //LocationPermissionScreen(this)

//                    //SocketManager().startSocket()
//                    ButtonChatSetMessage(viewModel)
                    val navController = rememberNavController()
                    SocketManager.navController = navController
                    val viewModelFactory = ChatViewModelFactory()
                    val chatViewModel = ViewModelProvider(this,viewModelFactory)[ChatViewModel::class.java]
                    SocketManager.setChatViewModel(chatViewModel)
                    AppNavHost(navController, chatViewModel)
                }
            }
            MyApp()
        }
    }
}
@Composable
fun MyApp() {
    PetWorkerTheme {

    }
}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun LocationPermissionScreen(mainActivity: MainActivity) {
    val context = LocalContext.current
    val permissionState = rememberPermissionState(android.Manifest.permission.ACCESS_FINE_LOCATION)

    LaunchedEffect(Unit) {
        if (!permissionState.status.isGranted) {
            permissionState.launchPermissionRequest()
        }
    }

    if (permissionState.status.isGranted) {
        StartLocationServiceButton(mainActivity)
    } else {
        Text("위치 정보 권한 필요")
    }
}

@Composable
fun StartLocationServiceButton(mainActivity: MainActivity) {
    val context = LocalContext.current
    val isServiceRunning = Observer<Boolean> { isRunning ->
        if (!isRunning) {
            GPSData.startGpsService(context)
            GPSData.isServiceRunning.postValue(true)
        }
    }

    Button(onClick = {
        GPSData.isServiceRunning.observe(mainActivity, isServiceRunning)
        GPSData.startGpsService(context)

    }) {
        Text("Start Location Service")
    }
}


@SuppressLint("SetJavaScriptEnabled")
@Composable
fun Greeting(context: Context, userViewModel: UserViewModel) {
    AndroidView(
        modifier = Modifier.fillMaxSize(),
        factory = { context ->
            WebView(context).apply {
                webViewClient = WebViewClient()
                settings.javaScriptEnabled = true
                settings.builtInZoomControls = true
                settings.defaultTextEncodingName = "utf-8"
            }
        },
        update = { webView ->
            webView.addJavascriptInterface(WebAppInterface(context,userViewModel),"Certification")
            webView.loadUrl("https://petpath.ritchko.com/certification/request")
        }
    )
}
@Composable
fun ButtonChatSetMessage(chatViewModel: ChatViewModel) {
    Column {
        // 버튼을 누르면 채팅 방에 들어가고 채팅 내역을 불러옴
        Button(onClick = {
            //SocketManager.enterChatRoom(1)

            //chatViewModel.getChatHistory(1)

        }) {
            Text(text = "Load Chat History")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // 채팅 내역을 화면에 표시
        val chatHistory by chatViewModel.chatHistoryList.collectAsState()

        LazyColumn {
            items(chatHistory) { chatMessage ->
                ChatMessageItem(chatMessage)
            }
        }
    }
}

@Composable
fun ChatMessageItem(chatMessage: ChatMessage) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Text(text = "Sender: ${chatMessage.sender}")
        chatMessage.content?.let { Text(text = it) }
        Divider()
    }
}
