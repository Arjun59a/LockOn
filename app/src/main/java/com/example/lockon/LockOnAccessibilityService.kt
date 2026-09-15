package com.example.lockon

import android.accessibilityservice.AccessibilityService
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.accessibility.AccessibilityEvent

class LockOnAccessibilityService : AccessibilityService() {

    private val TAG = "LOCKON"

    private var lockScreenShowing = false

    override fun onServiceConnected() {
        super.onServiceConnected()
        Log.i(TAG, "Accessibility Service Connected")
    }

    override fun onAccessibilityEvent(event: AccessibilityEvent?) {

        if (event?.eventType != AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED) {
            return
        }

        val packageName = event.packageName?.toString()

        Log.i(TAG, "Accessibility detected = $packageName")

        if (packageName == null) {
            return
        }

        // Our own app
        if (packageName == this.packageName) {
            lockScreenShowing = false
            return
        }

        val sharedPref = getSharedPreferences(
            "MyPrefsFile",
            Context.MODE_PRIVATE
        )

        // Check temporary unlock
        val temporaryUnlockedPackage =
            sharedPref.getString("TEMP_UNLOCKED_PACKAGE", null)

        if (packageName == temporaryUnlockedPackage) {
            Log.i(TAG, "App temporarily unlocked = $packageName")
            return
        }

        // If user moved to another app, remove temporary unlock
        if (temporaryUnlockedPackage != null &&
            packageName != temporaryUnlockedPackage
        ) {
            sharedPref.edit()
                .remove("TEMP_UNLOCKED_PACKAGE")
                .apply()

            Log.i(TAG, "Temporary unlock cleared")
        }

        // Check permanent lock
        val locked = sharedPref.getBoolean(
            packageName,
            false
        )

        Log.i(TAG, "Accessibility: Is app locked? = $locked")

        if (!locked) {
            return
        }

        if (lockScreenShowing) {
            Log.i(TAG, "LockScreen already showing")
            return
        }

        Log.i(TAG, "Accessibility: LOCKING $packageName")

        lockScreenShowing = true

        val intent = Intent(
            this,
            QuestionActivity::class.java
        )

        // Tell LockScreen which app we are unlocking
        intent.putExtra(
            "LOCKED_PACKAGE",
            packageName
        )

        intent.addFlags(
            Intent.FLAG_ACTIVITY_NEW_TASK
        )

        startActivity(intent)
    }

    override fun onInterrupt() {
        Log.i(TAG, "Accessibility Service Interrupted")
    }
}