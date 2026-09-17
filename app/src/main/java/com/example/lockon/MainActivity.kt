package com.example.lockon


import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.RecyclerView
import android.accessibilityservice.AccessibilityServiceInfo
import android.content.Context
import android.provider.Settings
import android.view.accessibility.AccessibilityManager

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val enableAccessibility = findViewById<Button>(R.id.enableAccessibility)

        enableAccessibility.setOnClickListener {
            startActivity(Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS))
        }
        val sharedPref = getSharedPreferences("MyPrefsFile", Context.MODE_PRIVATE)

        var rv = findViewById<RecyclerView>(R.id.rv_contact)
        val pm = packageManager
        val applist = ArrayList<Applist>()


        val apps = pm.getInstalledApplications(0)

        for (app in apps) {

            val launchIntent = pm.getLaunchIntentForPackage(app.packageName)

            if (launchIntent != null) {

                val appName = pm.getApplicationLabel(app).toString()
                val packageName = app.packageName
                val icon = pm.getApplicationIcon(app)

                val isSystemApp =
                    (app.flags and android.content.pm.ApplicationInfo.FLAG_SYSTEM) != 0

                if (!isSystemApp) {
                    applist.add(
                        Applist(
                            appName,
                            icon,
                            packageName,
                            sharedPref.getBoolean(packageName, false)
                        )
                    )
                }
            }
        }
        var arr1 : Array<Applist> = applist.toTypedArray()
        rv.adapter = AppInfoAdapter(arr1,sharedPref)

    }
    private fun isAccessibilityEnabled(): Boolean {
        val accessibilityManager =
            getSystemService(Context.ACCESSIBILITY_SERVICE) as AccessibilityManager

        return accessibilityManager.getEnabledAccessibilityServiceList(
            AccessibilityServiceInfo.FEEDBACK_ALL_MASK
        ).any {
            it.resolveInfo.serviceInfo.packageName == packageName &&
                    it.resolveInfo.serviceInfo.name == LockOnAccessibilityService::class.java.name
        }
    }

    private fun updateAccessibilityButton() {

        val button = findViewById<Button>(R.id.enableAccessibility)

        if (isAccessibilityEnabled()) {
            button.text = "Enabled ✓"
            button.isEnabled = false
        } else {
            button.text = "Enable"
            button.isEnabled = true
        }
    }

    override fun onResume() {
        super.onResume()
        updateAccessibilityButton()
    }
}