package com.petpath.walk

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.rememberNavController
import com.petpath.walk.chat.SocketManager
import com.petpath.walk.gps.GPSData
import com.petpath.walk.theme.PetWorkerTheme
import com.petpath.walk.viewModel.ChatViewModel
import com.petpath.walk.viewModel.ChatViewModelFactory
import com.petpath.walk.viewModel.UserViewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import com.petpath.walk.data.ApiRequest
import com.petpath.walk.data.SetPushTokenResponse
import com.petpath.walk.viewModel.KeyboardVisibilityViewModel
import com.petpath.walk.viewModel.UserViewModelFactory
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : ComponentActivity() {
    private val keyboardVisibilityViewModel: KeyboardVisibilityViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PetWorkerTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ViewCompat.setOnApplyWindowInsetsListener(findViewById(android.R.id.content)) { _, insets ->
                        val isKeyboardVisible = insets.isVisible(WindowInsetsCompat.Type.ime())
                        keyboardVisibilityViewModel.updateKeyboardVisibility(isKeyboardVisible)
                        insets
                    }
                    SocketManager.connectSocket()
                    FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
                        if (!task.isSuccessful) {
                            Log.w(TAG, "Fetching FCM registration token failed", task.exception)
                            return@OnCompleteListener
                        }

                        // Get new FCM registration token
                        val token = task.result
                        uploadToken(token)
                        // Log and toast
                        Log.d(TAG, token)
                        Toast.makeText(baseContext, token, Toast.LENGTH_SHORT).show()
                    })
                    val userViewModelFactory = UserViewModelFactory()
                    val userViewModel = ViewModelProvider(this,userViewModelFactory)[UserViewModel::class.java]
                    val context = LocalContext.current
//                    //LocationPermissionScreen(this)

//                    //SocketManager().startSocket()
//                    ButtonChatSetMessage(viewModel)
                    val navController = rememberNavController()
                    SocketManager.navController = navController
                    val viewModelFactory = ChatViewModelFactory()
                    val chatViewModel = ViewModelProvider(this,viewModelFactory)[ChatViewModel::class.java]
                    SocketManager.setChatViewModel(chatViewModel)
                    AppNavHost(navController, chatViewModel,keyboardVisibilityViewModel,context,userViewModel)
                }
            }
        }
    }

    companion object {
        fun uploadToken(token: String) {
            val service: ApiInterface = getRetrofit().create(ApiInterface::class.java)
            val setTokenRequest = ApiRequest(
                operation = "SetPushToken",
                param = mapOf("token" to token)
            )
            UserManager.userToken?.let<String, Call<List<SetPushTokenResponse>>> {
                service.setPushToken(
                    it,
                    listOf<ApiRequest>(setTokenRequest)
                )
            }?.enqueue(object : Callback<List<SetPushTokenResponse>> {
                override fun onResponse(
                    call: Call<List<SetPushTokenResponse>>,
                    response: Response<List<SetPushTokenResponse>>
                ) {
                    if (response.isSuccessful) {
                        val tokenResponse =
                            response.body()?.get(0)
                    } else {
                        // GetUnreadChatIndex API 오류 처리
                    }
                }

                override fun onFailure(call: Call<List<SetPushTokenResponse>>, t: Throwable) {
                    // 네트워크 오류 처리
                }
            })
        }
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



