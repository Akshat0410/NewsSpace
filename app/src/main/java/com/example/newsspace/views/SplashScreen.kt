package com.example.newsspace.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.newsspace.R
import android.content.Intent
import android.os.Handler


class SplashScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)


    }

    override fun onStart() {
        super.onStart()
        handleSplash()
    }

    override fun onResume() {
        super.onResume()
        handleSplash()
    }

    fun handleSplash(){
        Handler().postDelayed(Runnable {
            val i = Intent(this, SignInActivity::class.java)
            startActivity(i)
            finish()
        }, 5000)
    }
}