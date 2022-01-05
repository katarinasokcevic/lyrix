package com.example.lyrix

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SongsRecyclerAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var items: List<Song> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return SongViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.song, parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder) {
            is SongViewHolder -> {
                holder.bind(items[position])
            }
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun postItemsList(data: ArrayList<Song>) {
        items = data
    }

    class SongViewHolder constructor(
        itemView: View
    ): RecyclerView.ViewHolder(itemView) {
        private val songName: TextView = itemView.findViewById(R.id.songName)
        private val songArtist: TextView = itemView.findViewById(R.id.songArtist)
        fun bind(song: Song) {
            songName.text = song.name
            songArtist.text = song.artist
            itemView.setOnClickListener {
                Toast.makeText(itemView.context, "Dohvacam lyrics: " + song.artist + " - " + song.name, Toast.LENGTH_SHORT).show()
                val request =
                    FetchLyrics.buildService(FetchLyricsInterface::class.java)
                val call = request.getLyrics(artist = song.artist, title = song.name)
                call.enqueue(object : Callback<LyricsResponse> {
                    override fun onResponse(
                        call: Call<LyricsResponse>, response:
                        Response<LyricsResponse>
                    ) {
                        if (response.isSuccessful) {
                            val i = Intent(itemView.context, LyricsActivity::class.java)
                            i.putExtra("name", song.name)
                            i.putExtra("artist", song.artist)
                            i.putExtra("lyrics", response.body()!!.lyrics)
                            itemView.context.startActivity(i)
                        } else {
                            Toast.makeText(itemView.context, "Lyrics nije pronaden", Toast.LENGTH_LONG).show()
                        }
                    }
                    override fun onFailure(call: Call<LyricsResponse>, t: Throwable) {
                        Toast.makeText(itemView.context, "Greska: " + t.message.toString(), Toast.LENGTH_SHORT).show()
                    }
                })
            }
        }
    }
}