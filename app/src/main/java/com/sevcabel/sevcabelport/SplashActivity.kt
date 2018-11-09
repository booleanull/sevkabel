package com.sevcabel.sevcabelport

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        routeToOtherActivity()
        finish()
    }
    private fun routeToOtherActivity(){
        // ПОМЕНЯЙ НА АУФ АКТИВИТИ
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}
