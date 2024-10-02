package com.petpath.walk.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class KeyboardVisibilityViewModel : ViewModel() {
    private val _isKeyboardVisible = MutableLiveData(false)
    val isKeyboardVisible: LiveData<Boolean> get() = _isKeyboardVisible

    fun updateKeyboardVisibility(isVisible: Boolean) {
        _isKeyboardVisible.value = isVisible
    }
}