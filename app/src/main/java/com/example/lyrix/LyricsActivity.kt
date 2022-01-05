package com.example.lyrix

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.util.Log
import android.widget.TextView

class LyricsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.lyrics)

        val textSongName = findViewById<TextView>(R.id.placeHolderSongName)
        val textArtist = findViewById<TextView>(R.id.placeholderArtist)
        val textLyrics = findViewById<TextView>(R.id.placeholderLyrics)

        val bundle: Bundle? = intent.extras
        bundle?.let {
            bundle.apply {
                textSongName.text = getString("name", "")
                textArtist.text = getString("artist", "")
                textLyrics.text = getString("lyrics", "")
                textLyrics.movementMethod = ScrollingMovementMethod()
            }
        }
    }
}