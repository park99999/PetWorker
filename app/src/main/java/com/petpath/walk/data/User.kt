package com.petpath.walk.data

// APIFormatData.kt
data class CertUserResponse(
    val response: Int,
    val message: String,
    val data: CertUserInfo
)

// UserInfo.kt
data class CertUserInfo(
    val name: String? = null,
    val birthday: String?= null,
    val gender: String? = null,
    val phone: String?=null
)