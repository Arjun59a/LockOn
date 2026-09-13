
package com.example.lockon

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat

class LockOnService : Service() {

    private lateinit var appmonitor: Appmonitor

    override fun onCreate() {
        super.onCreate()

        // Create notification channel
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            val channel = NotificationChannel(
                "LOCKON_CHANNEL",
                "Lock-On Monitoring",
                NotificationManager.IMPORTANCE_LOW
            )

            val notificationManager =
                getSystemService(NotificationManager::class.java)

            notificationManager.createNotificationChannel(channel)
        }

        // Get SharedPreferences
        val sharedPref = getSharedPreferences(
            "MyPrefsFile",
            Context.MODE_PRIVATE
        )

        // Create Appmonitor
        appmonitor = Appmonitor(
            this,
            sharedPref
        )

        Log.i("LOCKON", "Service Created")
    }

    override fun onStartCommand(
        intent: Intent?,
        flags: Int,
        startId: Int
    ): Int {

        // Foreground service notification
        val notification = NotificationCompat.Builder(
            this,
            "LOCKON_CHANNEL"
        )
            .setContentTitle("Lock-On")
            .setContentText("App monitoring is running")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .build()

        // VERY IMPORTANT:
        // Foreground service must call this quickly
        startForeground(
            1,
            notification
        )

        Log.i("LOCKON", "Service Started")

        // Start monitoring only here
        appmonitor.startMonitoring()

        return START_STICKY
    }

    override fun onBind(intent: Intent): IBinder? {
        return null
    }
}


