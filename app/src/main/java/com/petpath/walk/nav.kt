package com.petpath.walk

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.petpath.walk.chat.ChatHistoryScreen
import com.petpath.walk.chat.ChatRoomsScreen
import com.petpath.walk.chat.MainScreen
import com.petpath.walk.data.ChatRoom
import com.petpath.walk.viewModel.ChatViewModel

@Composable
fun AppNavHost(navController: NavHostController, chatViewModel: ChatViewModel) {
    NavHost(navController, startDestination = "main") {
        composable("main") { MainScreen(navController) }
        composable("chatRooms") { ChatRoomsScreen(navController, chatRooms = listOf(
            ChatRoom(1, "General", "Hello World"),
            ChatRoom(2, "Support", "How can we help?")
        )) }
        composable("chatHistory/{chatId}") { backStackEntry ->
            val chatId = backStackEntry.arguments?.getString("chatId")?.toInt() ?: 0
            ChatHistoryScreen(chatViewModel, chatId)
        }
    }
}