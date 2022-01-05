package com.example.lyrix

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textfield.TextInputEditText
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BrowseActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.browse)
    }

    // Ova metoda se poziva tek kada korisnik pritisne Pretrazi unutar Browse layouta.
    fun search(view: View) {
        val searchText = findViewById<TextInputEditText>(R.id.searchText);
        val request = Discogs.buildService(DiscogsInterface::class.java)
        val call = request.search(searchText.text.toString())
        call.enqueue(object : Callback<DiscogsSearchResponseData> {
            override fun onResponse(
                call: Call<DiscogsSearchResponseData>, response:
                Response<DiscogsSearchResponseData>
            ) {
                if (response.isSuccessful) {
                    findViewById<RecyclerView>(R.id.listOfSongs).apply {
                        layoutManager = LinearLayoutManager(this@BrowseActivity)
                        val tmpAdapter = SongsRecyclerAdapter()
                        val songs = response.body()!!.results
                        if (songs.size == 0) {
                            Toast.makeText(view.context, "Nema rezultata", Toast.LENGTH_LONG).show()
                            return
                        }
                        val uniqueSongs = HashSet<String>()
                        val finalSongs = ArrayList<Song>()
                        for (songResponse in songs) {
                            if (uniqueSongs.contains(songResponse.title)) {
                                continue
                            }
                            uniqueSongs.add(songResponse.title)
                            // razdvoji autora i naziv pjesme, tako da ju mozemo lijepse ispisati
                            val splitItems = songResponse.title.split("-", limit=2)
                            if (splitItems.size != 2) {
                                continue
                            }
                            val song = Song(splitItems[1].trim(), splitItems[0].trim())
                            // izfiltriraj pjesme koje imaju praznog autora ili naziv pjesme, te
                            // autore i pjesme koje imaju zagrade
                            if (song.artist == "" || song.name == "" || song.name.contains("(") || song.artist.contains("(")) {
                                continue
                            }
                            finalSongs.add(song)
                        }
                        tmpAdapter.postItemsList(finalSongs)
                        adapter = tmpAdapter
                    }
                } else {
                    Toast.makeText(view.context, "Greska: " + response.message(), Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<DiscogsSearchResponseData>, t: Throwable) {
                Toast.makeText(view.context, "Greska: " + t.message.toString(), Toast.LENGTH_SHORT).show()
            }
        })
    }
}