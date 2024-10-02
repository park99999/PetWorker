package com.petpath.walk.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.petpath.walk.ApiInterface
import com.petpath.walk.data.ApiRequest
import com.petpath.walk.data.CertUserInfo
import com.petpath.walk.data.CertUserResponse
import com.petpath.walk.data.SignUpResponse
import com.petpath.walk.getRetrofit
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import retrofit2.Call
import retrofit2.Response

sealed class UserNavigationEvent {
    data object EmailSetting : UserNavigationEvent()
    data object GoToVerifyFailed : UserNavigationEvent()
    data object  SignUPCompleted : UserNavigationEvent()
}
class UserViewModelFactory(): ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UserViewModel::class.java)) {
            return UserViewModel() as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}


class UserViewModel :ViewModel(){
//    private val _certuserInfo = MutableStateFlow(CertUserInfo())
//    val certUserInfo :StateFlow<CertUserInfo> = _certuserInfo.asStateFlow()

    private val _navigationEvent = MutableStateFlow<UserNavigationEvent?>(null)
    val navigationEvent: StateFlow<UserNavigationEvent?> = _navigationEvent.asStateFlow()

    private val _uid = MutableLiveData<String>()
    val uid: LiveData<String> = _uid

    private val _email = MutableLiveData<String>()
    val email: LiveData<String> = _email

    private val _password = MutableLiveData<String>()
    val password: LiveData<String> = _password

    private fun setUserId(id: String) {
        _uid.postValue(id)
    }
    fun setNavigationEvent(){
        _navigationEvent.value = null
    }
    fun getUserId(): String? {
        return _uid.value
    }
    fun setPassword(id: String) {
        _password.postValue(id)
    }

    fun getEmail(): String? {
        return _email.value
    }
    fun setEmail(id: String) {
        _email.postValue(id)
    }

    fun getPassword(): String? {
        return _password.value
    }

    val service: ApiInterface =  getRetrofit().create(ApiInterface::class.java)



    fun getCertUserInfo(uid :String){
        val certUser = ApiRequest(
            operation = "GetCertInfo",
            param = uid
        )
        setUserId(uid)
        service.getUserInfo(listOf(certUser))
            .enqueue( object : retrofit2.Callback<List<CertUserResponse>>{
                override fun onResponse(
                    call: Call<List<CertUserResponse>>,
                    response: Response<List<CertUserResponse>>
                ) {
                    if(response.isSuccessful) {
                        val resp = response.body()
                        val data = resp!![0].data
                        when (resp[0].errorCode) {
                            null -> _navigationEvent.value = UserNavigationEvent.EmailSetting
                            "NotAdult" -> _navigationEvent.value = UserNavigationEvent.GoToVerifyFailed
                        }
                    }
                    else{
                        Log.d("getCertUserInfo", response.toString())
                    }
                }

                override fun onFailure(call: Call<List<CertUserResponse>>, t: Throwable) {
                    Log.d("getCertUserInfo", t.message.toString())
                }

            })
    }

    fun signUp(){
        val signUpUser = ApiRequest(
            operation = "SignUp",
            param = mapOf("imp_uid" to getUserId(), "email" to getEmail(), "pw" to getPassword())
        )
        service.signUp(listOf(signUpUser))
            .enqueue( object : retrofit2.Callback<List<SignUpResponse>>{
            override fun onResponse(
                call: Call<List<SignUpResponse>>,
                response: Response<List<SignUpResponse>>
            ) {
                if(response.isSuccessful) {
                    val resp = response.body()
                    when (resp?.get(0)?.response) {
                        200 -> _navigationEvent.value = UserNavigationEvent.SignUPCompleted
                        400 -> _navigationEvent.value = UserNavigationEvent.GoToVerifyFailed
                    }
                }
                else{

                }
            }

            override fun onFailure(call: Call<List<SignUpResponse>>, t: Throwable) {

            }

        })
    }
}