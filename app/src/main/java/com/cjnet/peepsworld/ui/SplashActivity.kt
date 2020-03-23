package com.cjnet.peepsworld.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.cjnet.peepsworld.R

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val timer: Thread = object : Thread() {
            override fun run() {
                try {
                    sleep(3000)
                } catch (e: Exception) {
                    e.printStackTrace()
                } finally {
                    callMainActivity()
                }
            }
        }
        timer.start()
    }
    fun callMainActivity() {
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }

}
