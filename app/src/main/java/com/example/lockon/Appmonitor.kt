
package com.example.lockon

import android.Manifest
import android.app.usage.UsageStatsManager
import android.content.Context
import android.content.SharedPreferences
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.annotation.RequiresPermission

class Appmonitor(
    private val context: Context,
    private var sharedpref: SharedPreferences
) {

    private val TAG = "LOCKON"

    private val handler = Handler(Looper.getMainLooper())

    // Prevent multiple monitoring loops
    private var monitoringStarted = false

    private val monitorRunnable = object : Runnable {

        @RequiresPermission(Manifest.permission.PACKAGE_USAGE_STATS)
        override fun run() {

            recentlyapplocked()

            // Check again after 1 second
            handler.postDelayed(this, 1000)
        }
    }

    fun startMonitoring() {

        // If monitoring is already running,
        // don't start another Runnable loop.
        if (monitoringStarted) {
            Log.i(TAG, "Monitoring already running")
            return
        }

        monitoringStarted = true

        Log.i(TAG, "Monitoring Started")

        handler.post(monitorRunnable)
    }

    fun getUsagestateManager(): UsageStatsManager {

        return context.getSystemService(
            Context.USAGE_STATS_SERVICE
        ) as UsageStatsManager
    }

    @RequiresPermission(Manifest.permission.PACKAGE_USAGE_STATS)
    fun getCurrentPackageName(): String? {

        val usageStatsManager = getUsagestateManager()

        val endTime = System.currentTimeMillis()
        val startTime = endTime - 1000 * 10

        val usageStats = usageStatsManager.queryUsageStats(
            UsageStatsManager.INTERVAL_DAILY,
            startTime,
            endTime
        )

        if (usageStats.isEmpty()) {

            Log.i(TAG, "Usage stats is EMPTY")

            return null
        }

        val recentApp = usageStats.maxByOrNull {
            it.lastTimeUsed
        }

        Log.i(TAG, "getCurrentPackageName: $recentApp")

        return recentApp?.packageName
    }

    fun isAppLocked(packageName: String?): Boolean {

        return sharedpref.getBoolean(
            packageName,
            false
        )
    }

    @RequiresPermission(Manifest.permission.PACKAGE_USAGE_STATS)
    fun recentlyapplocked() {

        Log.i(TAG, "CHECK STARTED +++++")

        val currpck = getCurrentPackageName()

        Log.i(TAG, "Detected package = $currpck")

        if (currpck != null) {

            val locked = isAppLocked(currpck)

            Log.i(TAG, "Is this app locked? = $locked")

            if (locked) {

                Log.i(TAG, "Your App is locked")

            }
        }
    }
}

