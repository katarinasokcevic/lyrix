package com.example.lyrix

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.os.Handler


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Handler().postDelayed(Runnable {
            // Start your app main activity
            val i = Intent(this@MainActivity, BrowseActivity::class.java)
            startActivity(i)
            // close this activity
            finish()
        }, 1000)
    }
}