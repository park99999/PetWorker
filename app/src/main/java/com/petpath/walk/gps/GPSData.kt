package com.petpath.walk.gps

import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.MutableLiveData

object GPSData {

    // 서비스의 실행 성태
    val isServiceRunning = MutableLiveData<Boolean>(false)

    // 위치 데이터를 위한 변수들
    var lat = 0.0
    var lon = 0.0

    fun startGpsService(context: Context) {
        // 서비스가 실행 중이지 않은 경우에만 서비스를 시작한다.
        // 중복 동작과 ANR 방지 목적이다.
        if(!isServiceRun(context)) {
            val intent = Intent(context, LocationForegroundService::class.java)
            ContextCompat.startForegroundService(context, intent)
            Toast.makeText(context, "Service Start", Toast.LENGTH_SHORT).show()
        }
    }
    // LocationService 중지하는 메서드
    fun stopGpsService(context: Context) {
        val intent = Intent(context, LocationService::class.java)
        context.stopService(intent)
    }
    private fun isServiceRun(context: Context): Boolean {
        return isServiceRunning.value == true
    }

}