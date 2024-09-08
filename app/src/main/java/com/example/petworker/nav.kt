package com.example.petworker

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.petworker.chat.ChatHistoryScreen
import com.example.petworker.chat.ChatRoomsScreen
import com.example.petworker.chat.MainScreen
import com.example.petworker.data.ChatRoom
import com.example.petworker.viewModel.ChatViewModel

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