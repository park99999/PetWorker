package com.petpath.walk

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.petpath.walk.chat.data.ChatHistory
import com.petpath.walk.chat.data.ChatHistoryDAO

@Database(entities = [ChatHistory::class], version = 2)
abstract class ChatHistoryDatabase : RoomDatabase() {
    abstract fun chatHistoryDao(): ChatHistoryDAO
    companion object {
        private var instance: ChatHistoryDatabase? = null

        @Synchronized
        fun getInstance(context: Context): ChatHistoryDatabase? {
            if (instance == null) {
                synchronized(ChatHistoryDatabase::class){
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        ChatHistoryDatabase::class.java,
                        "chatHistory-database"
                    ).build()
                }
            }
            return instance
        }
    }
}
