package com.example.petworker.gps

import android.Manifest
import android.app.Service
import android.content.Intent
import android.content.pm.PackageManager
import android.os.IBinder
import android.os.Looper
import android.util.Log
import androidx.core.app.ActivityCompat
import com.example.petworker.gps.GPSData.lat
import com.example.petworker.gps.GPSData.lon
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority

class LocationService : Service() {

    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var locationRequest: LocationRequest


    //다른 서비스와 바인딩
    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        // 5초 마다 위치 정보 갱신
        locationRequest = LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY,5000).setMinUpdateIntervalMillis(3000).build()
        startLocationUpdates()
    }

    private fun startLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return
        }
        val locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                locationResult.lastLocation?.let {
                    // 위치 업데이트 로그 표시
                    lat = it.latitude
                    lon = it.longitude
                    Log.d("LocationService", "Location: ${lat}, ${lon}")
                }
            }
        }
        fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper())
    }
    private val locationCallback: LocationCallback = object : LocationCallback() {
        // 위치 정보 업데이트 시 호출됨
        // locationResult 객체는 최근의 위치 정보를 가짐
        override fun onLocationResult(locationResult: LocationResult) {
            // locationResult.lactions[0]이 최신 위치 객체
            if (locationResult.locations[0] != null) {
                lat = locationResult.locations[0].latitude
                lon = locationResult.locations[0].longitude
            }
        }
    }
    fun removeLocationUpdates() {
        // 위치 업데이트 중지 처리 로직
        fusedLocationClient.removeLocationUpdates(locationCallback)
    }
    //서비스가 시작할 떄 호출
    //Start_STICKY -> 시스템이 서비스를 종료한 후에도 서비스를 다시 시작하도록 요청
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return START_STICKY
    }
}