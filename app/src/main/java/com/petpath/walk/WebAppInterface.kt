package com.petpath.walk

import android.content.Context
import android.webkit.JavascriptInterface
import com.petpath.walk.viewModel.UserViewModel

class WebAppInterface(private val mContext: Context,private val userViewModel: UserViewModel) {
    @JavascriptInterface
    fun complete(uid : String){
        userViewModel.getCertUserInfo((uid))
    }
}