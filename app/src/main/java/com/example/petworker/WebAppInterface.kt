package com.example.petworker

import android.content.Context
import android.webkit.JavascriptInterface
import com.example.petworker.viewModel.UserViewModel

class WebAppInterface(private val mContext: Context,private val userViewModel: UserViewModel) {
    @JavascriptInterface
    fun complete(uid : String){
        //userViewModel.getCertUserInfo((uid))
    }
}