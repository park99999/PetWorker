package com.petpath.walk.screen.register

import android.annotation.SuppressLint
import android.content.Context
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.petpath.walk.R
import com.petpath.walk.WebAppInterface
import com.petpath.walk.chat.SocketManager.navController
import com.petpath.walk.screen.util.CustomButton
import com.petpath.walk.screen.util.TopBarComponent
import com.petpath.walk.viewModel.UserNavigationEvent
import com.petpath.walk.viewModel.UserViewModel

@Composable
fun RegisterVerify(navController: NavHostController) {
    Column(modifier = Modifier.fillMaxSize()) {
        TopBarComponent(
            R.drawable.ic_backbtn,
            R.drawable.empty,
            R.drawable.empty,
            R.drawable.ic_close,"회원가입"){
            navController.popBackStack()
        }
        Column(modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .padding(top = 76.dp)) {
            Text("안전한 펫워킹을 위해", style = MaterialTheme.typography.titleMedium)
            Text("본인인증을 진행해주세요!",style = MaterialTheme.typography.titleMedium, modifier = Modifier.padding(top = 8.dp))
        }
        Spacer(modifier = Modifier.weight(1f))
        CustomButton(true,"본인인증 하러가기") {
            navController.navigate("verifyAPI")
        }
    }
}

@Composable
fun VerifiyFailedScreen(navController: NavHostController) {
    Column(modifier = Modifier.fillMaxSize()) {
        TopBarComponent(
            R.drawable.ic_backbtn,
            R.drawable.empty,
            R.drawable.empty,
            R.drawable.ic_close,"회원가입"){
            navController.popBackStack()
        }
        Column(modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .padding(top = 76.dp)) {
            Text("19세 미만은 펫패스를", style = MaterialTheme.typography.titleMedium)
            Text("이용할 수 없어요",style = MaterialTheme.typography.titleMedium, modifier = Modifier.padding(top = 8.dp))
        }
        Spacer(modifier = Modifier.weight(1f))
        CustomButton(true,"로그인으로 돌아가기") {
            navController.popBackStack(route = "login",inclusive = true)
        }
    }
}
@SuppressLint("SetJavaScriptEnabled")
@Composable
fun VerifyScreen(navController: NavHostController, context: Context, userViewModel: UserViewModel) {
    val navigationEvent by userViewModel.navigationEvent.collectAsState()
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
    LaunchedEffect(navigationEvent) {
        when (navigationEvent) {
            is UserNavigationEvent.EmailSetting -> {
                navController.navigate("emailSetting")
                userViewModel.setNavigationEvent()
            }
            is UserNavigationEvent.GoToVerifyFailed -> {
                navController.navigate("verifyFailed")
                userViewModel.setNavigationEvent()
            }
            null -> {
                // 아무 이벤트가 없는 경우
            }
            UserNavigationEvent.SignUPCompleted -> {
                navController.navigate("signupCompleted")
                userViewModel.setNavigationEvent()
            }
        }
    }
}
@Preview
@Composable
fun Test(modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    RegisterVerify(navController)
}