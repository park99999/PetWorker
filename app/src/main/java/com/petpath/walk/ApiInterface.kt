package com.petpath.walk

import com.petpath.walk.data.ApiRequest
import com.petpath.walk.data.CertUserResponse
import com.petpath.walk.data.ChatHistoryResponse
import com.petpath.walk.data.FileUploadResponse
import com.petpath.walk.data.SetPushTokenResponse
import com.petpath.walk.data.UnreadChatIndexResponse
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

    @POST("SetPushToken")
    fun setPushToken(
        @Header("auth") authToken: String,
        @Body apiRequest: List<ApiRequest>
    ): Call<List<SetPushTokenResponse>>
}