package com.petpath.walk.chat.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ChatHistoryDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertItem(chatHistory: ChatHistory)

    @Delete
    suspend fun deleteItem(chatHistory: ChatHistory)

    @Query("SELECT MAX(chatIndex) FROM chatHistory where chatRoomId = :chatRoomId ")
    suspend fun getChatLastIndex(chatRoomId : Long): Int?
    @Query("select * from chatHistory where chatRoomId = :chatRoomId order by chatIndex desc")
    suspend fun getAllChatHistory(chatRoomId : Long) : List<ChatHistory>
}

