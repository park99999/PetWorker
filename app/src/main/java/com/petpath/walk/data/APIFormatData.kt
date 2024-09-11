package com.petpath.walk.data

data class ApiRequest(
    val operation: String,
    val param: Any //
)

data class ApiResponse(
    val response: Int,
    val message: String,
    val errorCode: String?,
    val target: String?,
    val data: UnreadChatIndexResponse
)


