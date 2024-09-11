package com.petpath.walk.gps

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import com.petpath.walk.MainActivity
import com.petpath.walk.R
import com.petpath.walk.gps.GPSData.isServiceRunning

class LocationForegroundService : Service() {

    private val channelId = "LocationServiceChannel"

    override fun onCreate() {
        super.onCreate()
        Log.d("LocationService", "LocationForegroundService")
    }

    private fun createNotificationChannel(): NotificationCompat.Builder {
        val serviceChannel = NotificationChannel(
            channelId,
            "Location Service Channel",
            NotificationManager.IMPORTANCE_DEFAULT
        )
        val manager = getSystemService(NotificationManager::class.java)
        manager.createNotificationChannel(serviceChannel)
        val intent = Intent(this, MainActivity::class.java).apply {
        }

        val pendingIntent =
            PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE)
        return NotificationCompat.Builder(this, channelId)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("Location Service")
            .setContentText("위치 추적중")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent)
    }

    @SuppressLint("ForegroundServiceType")
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        startForeground(1, createNotificationChannel().build())
        isServiceRunning.postValue(true)
        // LocationService 시작
        val locationServiceIntent = Intent(this, LocationService::class.java)
        startService(locationServiceIntent)
        return START_STICKY
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onDestroy() {
        super.onDestroy()
        isServiceRunning.postValue(false)
        //리소스 해제
    }
}