package com.hamdy.showtime.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.hamdy.showtime.R
import com.hamdy.showtime.ui.ui.MainActivity

class Splash : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash)
        val intent= Intent(this, MainActivity::class.java)
        val t: Thread = object : Thread() {
            override fun run() {
                try {
                    sleep(1000)
                    startActivity(intent)
                    finish()
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }
            }
        }
        t.start()
    }
}