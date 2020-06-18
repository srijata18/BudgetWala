package com.example.budgetwala.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.example.budgetwala.R
import com.example.budgetwala.userProfile.views.SalaryFragment

class SplashActivity : AppCompatActivity() {
    private val TIME_OUT = 3000L
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        val handler = Handler()
        val r: Runnable = object : Runnable {
            override fun run() {
                handler.postDelayed(this, TIME_OUT)
                val i = Intent(this@SplashActivity, SalaryFragment::class.java)
                startActivity(i)
                finish()
            }
        }
        r.run()
    }
}