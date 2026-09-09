package com.example.lockon

import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.RecyclerView

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
        val sharedPref = getSharedPreferences("MyPrefsFile", Context.MODE_PRIVATE)

        val rv = findViewById<RecyclerView>(R.id.rv_contact)
        val pm = packageManager
        val applist = ArrayList<Applist>()
        val monitor = Appmonitor(this,sharedPref)
        val apps = pm.getInstalledApplications(PackageManager.GET_META_DATA)
        for (app in apps) {

            val launchIntent = pm.getLaunchIntentForPackage(app.packageName)

            if (launchIntent != null) {

                val appName = pm.getApplicationLabel(app).toString()
                val packageName = app.packageName
                val icon = pm.getApplicationIcon(app)
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

        monitor.startMonitoring()
        rv.adapter = AppInfoAdapter(applist.toTypedArray(),sharedPref)
    }
}