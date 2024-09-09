package com.example.petworker.chat.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "chatHistory")
data class ChatHistory(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val chatRoomId : Long,
    val chatIndex: Long,
    val sender: Long,
    val type : String,
    val content : String,
    val createAt : String
)
