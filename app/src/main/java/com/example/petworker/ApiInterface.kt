package com.example.petworker

import com.example.petworker.data.ApiRequest
import com.example.petworker.data.CertUserResponse
import com.example.petworker.data.ChatHistoryResponse
import com.example.petworker.data.FileUploadResponse
import com.example.petworker.data.UnreadChatIndexResponse
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface ApiInterface {
    @POST("/API")
    fun callApi(
        @Header("auth") authToken: String,
        @Body apiRequest: List<ApiRequest>
    ): Call<List<Any>>
    @POST("GetCertInfo")
    fun getUserInfo(
        @Header("auth") authToken: String,
        @Body apiRequest: List<ApiRequest>
    ) : Call<List<CertUserResponse>>

    @POST("GetChatHistory")
    fun getChatHistory(
        @Header("auth") authToken: String,
        @Body apiRequest: List<ApiRequest>
    ): Call<List<ChatHistoryResponse>>
    @POST("GetUnreadChatIndex")
    fun getUnreadChatIndex(
        @Header("auth") authToken: String,
        @Body apiRequest: List<ApiRequest>
    ): Call<List<UnreadChatIndexResponse>>

    @Multipart
    @POST("fileUpload")
    fun uploadImage(
        @Part file: MultipartBody.Part
    ): Call<FileUploadResponse>
}