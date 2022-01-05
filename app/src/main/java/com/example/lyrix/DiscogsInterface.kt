package com.example.lyrix

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface DiscogsInterface {
    @GET("database/search?per_page=50")
    @Headers("Content-Type: application/json", "Authorization: Discogs token=NmolQsFvkWEgAUDMbHxgPeKzYECLhfNBdXSWrPCj")
    fun search(@Query("q") query: String): Call<DiscogsSearchResponseData>
}
data class DiscogsSearchResponseData(
    val results: ArrayList<SongResponse>,
)
data class SongResponse(
    val title: String,
)

data class Song(
    var name: String,
    var artist: String,
)