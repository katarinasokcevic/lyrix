package com.example.lyrix

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path

interface FetchLyricsInterface {
    @GET("{artist}/{title}")
    @Headers("Content-Type: application/json")
    fun getLyrics(@Path("artist") artist: String, @Path("title") title: String): Call<LyricsResponse>
}

data class LyricsResponse(
    val lyrics: String,
)
