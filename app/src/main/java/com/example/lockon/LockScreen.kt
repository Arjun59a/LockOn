package com.example.lockon

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class LockScreen : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        setContentView(R.layout.activity_lock_screen)

        ViewCompat.setOnApplyWindowInsetsListener(
            findViewById(R.id.lockscreen)
        ) { v, insets ->

            val systemBars =
                insets.getInsets(
                    WindowInsetsCompat.Type.systemBars()
                )

            v.setPadding(
                systemBars.left,
                systemBars.top,
                systemBars.right,
                systemBars.bottom
            )

            insets
        }

        val sharedPref = getSharedPreferences(
            "MyPrefsFile",
            MODE_PRIVATE
        )

        val savedPin =
            sharedPref.getString("MyLockPin", null)

        val enteredPin =
            findViewById<EditText>(R.id.et_pin)

        val errorText =
            findViewById<TextView>(R.id.tv_error)

        val unlockButton =
            findViewById<Button>(R.id.btn_unlock)

        unlockButton.setOnClickListener {

            val userPin =
                enteredPin.text.toString()

            if (userPin == savedPin) {

                errorText.visibility = View.GONE

                // Get the app that was locked
                val lockedPackage =
                    intent.getStringExtra("LOCKED_PACKAGE")

                // Temporarily unlock that app
                if (lockedPackage != null) {

                    sharedPref.edit()
                        .putString(
                            "TEMP_UNLOCKED_PACKAGE",
                            lockedPackage
                        )
                        .apply()
                }

                finish()

            } else {

                errorText.text = "Incorrect PIN"

                errorText.visibility = View.VISIBLE
            }
        }
    }
}