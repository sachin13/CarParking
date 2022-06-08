package com.example.carparking

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen

class SplashScreenActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        startActivity(Intent(this, OnBoardingActivity::class.java))
        finish()
        installSplashScreen()
        super.onCreate(savedInstanceState)
    }
}