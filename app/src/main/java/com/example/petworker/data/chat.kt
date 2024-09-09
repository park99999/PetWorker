package com.example.petworker.data

data class FileUploadResponse(
    val response: Int,
    val message: String,
    val data: String
)

data class UnreadChatIndexResponse(
    val response: Int,
    val message: String,
    val data: UnreadChatIndexData
)

data class UnreadChatIndexData(
    val untilReadIndex: Int ? =0,
    val otherSideReadIndex: Int?=0,
    var lastChatIndex: Int?=0
)
data class ChatMessage(
    val chatIndex : Long?=null,
    val sender : Long?=null,
    val type : String ?= null,
    val content : String ?= null,
    val createAt : String ?=null
)
data class ChatHistoryResponse(
    val response: Int,
    val message: String,
    val data: List<ChatMessage>
)


data class ChatRoom(
    val chatRoomId : Long ?= null,
    val chatRoomTitle : String ?= null,
    val chatRoomLastChat : String ?= null
)