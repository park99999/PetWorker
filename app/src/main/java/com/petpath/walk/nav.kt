package com.petpath.walk

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.petpath.walk.chat.ChatHistoryScreen
import com.petpath.walk.chat.ChatRoomsScreen
import com.petpath.walk.chat.MainScreen
import com.petpath.walk.data.ChatRoom
import com.petpath.walk.screen.find.FindIdScreen
import com.petpath.walk.screen.find.FindPwScreen
import com.petpath.walk.screen.login.LoginScreen
import com.petpath.walk.screen.register.EmailSettingScreen
import com.petpath.walk.screen.register.PasswordSettingScreen
import com.petpath.walk.screen.register.RegisterAgreement
import com.petpath.walk.screen.register.RegisterVerify
import com.petpath.walk.screen.register.SignUpCompletedScreen
import com.petpath.walk.screen.register.VerifiyFailedScreen
import com.petpath.walk.screen.register.VerifyScreen
import com.petpath.walk.viewModel.ChatViewModel
import com.petpath.walk.viewModel.KeyboardVisibilityViewModel
import com.petpath.walk.viewModel.UserViewModel

@Composable
fun AppNavHost(
    navController: NavHostController,
    chatViewModel: ChatViewModel,
    keyboardVisibilityViewModel: KeyboardVisibilityViewModel,
    context: Context,
    userViewModel: UserViewModel
) {
    NavHost(navController, startDestination = "login") {
        composable("main") { MainScreen(navController) }
        composable("chatRooms") { ChatRoomsScreen(navController, chatRooms = listOf(
            ChatRoom(1, "General", "Hello World"),
            ChatRoom(2, "Support", "How can we help?")
        )) }
        composable("chatHistory/{chatId}") { backStackEntry ->
            val chatId = backStackEntry.arguments?.getString("chatId")?.toInt() ?: 0
            ChatHistoryScreen(chatViewModel, chatId)
        }
        composable("registerVerify"){ RegisterVerify(navController) }
        composable("login"){ LoginScreen(keyboardVisibilityViewModel,navController)}
        composable("registerAgreement"){ RegisterAgreement(navController) }
        composable("verifyAPI"){ VerifyScreen(navController, context,userViewModel)}
        composable("verifyFailed"){ VerifiyFailedScreen(navController)}
        composable("emailSetting"){ EmailSettingScreen(navController,userViewModel)}
        composable("passwordSetting"){ PasswordSettingScreen(navController,userViewModel)}
        composable("signupCompleted"){ SignUpCompletedScreen(navController,userViewModel)}
        composable("findId"){ FindIdScreen(navController)}
        composable("findPw"){ FindPwScreen(navController) }
    }
}