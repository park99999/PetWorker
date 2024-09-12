package com.petpath.walk


import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

const val BASE_URL = "https://petpath.ritchko.com/API/"
const val SOCKET_URL = "https://petpath.ritchko.com/socket/"
fun getRetrofit(): Retrofit {
    val logging = HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }
    val httpClient = OkHttpClient.Builder().addInterceptor(logging).build()

    return Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(httpClient)
        .build()
}
