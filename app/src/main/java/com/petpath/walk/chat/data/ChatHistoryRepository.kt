package com.petpath.walk.chat.data

import com.petpath.walk.ChatHistoryDatabase

class ChatHistoryRepository(private val db : ChatHistoryDatabase){
    private val chatHistoryDAO = db.chatHistoryDao()
    suspend fun InsertItem(chatHistory: ChatHistory){
        chatHistoryDAO.insertItem(chatHistory)
    }
    suspend fun DeleteItem(chatHistory: ChatHistory){
        chatHistoryDAO.deleteItem(chatHistory)
    }
}
