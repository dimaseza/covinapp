package com.daivers.covinapp

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler

class SplashActivity : AppCompatActivity() {

    companion object {
        const val PREFS_TAG = "prefs"
        const val FIRST_TAG = "firstStart"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val prefs = getSharedPreferences(PREFS_TAG, Context.MODE_PRIVATE)
        val firstStart: Boolean = prefs.getBoolean(FIRST_TAG, true)

        Handler().postDelayed({
            kotlin.run {
                val intent = if (firstStart) Intent(this, GreetingsActivity::class.java)
                else Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
        }, 1500)
    }
}
