package com.daivers.covinapp

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import androidx.core.content.edit
import androidx.databinding.DataBindingUtil
import com.daivers.covinapp.databinding.ActivityGreetingsBinding
import com.daivers.covinapp.databinding.ActivityMainBinding

class GreetingsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityGreetingsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_greetings)

        val prefs = getSharedPreferences(SplashActivity.PREFS_TAG, Context.MODE_PRIVATE)

        binding.btnStart.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            prefs.edit {
                putBoolean(SplashActivity.FIRST_TAG, false)
                apply()
            }
            startActivity(intent)
            finish()
        }
    }
}
