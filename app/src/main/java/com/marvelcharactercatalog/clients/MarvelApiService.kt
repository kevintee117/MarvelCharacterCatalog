package com.marvelcharactercatalog.clients

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MarvelApiService {
    @GET("comics/{comicId}")
    fun getComic(
        @Path("comicId") comicId: Int,
        @Query("apikey") apiKey: String?,
        @Query("ts") timeStamp: String?,
        @Query("hash") hash: String?
    ): Call<ComicResponse>
}