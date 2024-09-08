package com.example.petworker.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.petworker.ApiInterface
import com.example.petworker.data.CertUserInfo
import com.example.petworker.getRetrofit
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class UserViewModelFactory(): ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UserViewModel::class.java)) {
            return UserViewModel() as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}


class UserViewModel :ViewModel(){
    private val _certuserInfo = MutableStateFlow(CertUserInfo())
    val certUserInfo :StateFlow<CertUserInfo> = _certuserInfo.asStateFlow()
    val service: ApiInterface =  getRetrofit().create(ApiInterface::class.java)



//    fun getCertUserInfo(uid :String){
//        service.getUserInfo(listOf(ApiBody("GetCertInfo", Uid(uid))))
//            .enqueue( object : retrofit2.Callback<List<CertUserResponse>>{
//                override fun onResponse(
//                    call: Call<List<CertUserResponse>>,
//                    response: Response<List<CertUserResponse>>
//                ) {
//                    if(response.isSuccessful) {
//                        val resp = response.body()
//                        val data = resp!![0].data
//                        _certuserInfo.update {
//                            it.copy(
//                                name = data.name,
//                                birthday = data.birthday,
//                                gender = data.gender,
//                                phone = data.gender
//                            )
//                        }
//                        Log.d("getCertUserInfo",resp.toString())
//                    }
//                    else{
//                        Log.d("getCertUserInfo", response.toString())
//                    }
//                }
//
//                override fun onFailure(call: Call<List<CertUserResponse>>, t: Throwable) {
//                    Log.d("getCertUserInfo", t.message.toString())
//                }
//
//            })
//    }
}