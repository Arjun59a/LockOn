package com.example.lockon

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class SetALock : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_seta_lock)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.secondactivity)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        var tverror : TextView = findViewById<TextView>(R.id.tvError)
        val sharedPref = getSharedPreferences("MyPrefsFile", Context.MODE_PRIVATE)

        findViewById<Button>(R.id.btnSavePin).setOnClickListener {
            val pin1 = findViewById<EditText>(R.id.etPin).text.toString()
            val pin2 = findViewById<EditText>(R.id.etConfirmPin).text.toString()
            if ((pin1.length == 4) && (pin2.length ==4))
            {

                if (pin1 == pin2)
                {
                    sharedPref.edit().
                    putString("MyLockPin",pin1)
                        .apply()

                    finish()

                }
                else
                {
                    tverror.text = "Confirm PIN and Actual PIN IS not matching"
                    tverror.visibility = View.VISIBLE


                }

            }
            else
            {
                tverror.text = "Length of the Pin Must be 4"
                tverror.visibility = View.VISIBLE

            }
        }

    }

}