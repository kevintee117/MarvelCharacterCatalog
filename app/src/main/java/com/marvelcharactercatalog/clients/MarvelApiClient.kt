package com.marvelcharactercatalog.clients

import com.marvelcharactercatalog.BuildConfig
import com.marvelcharactercatalog.utils.MarvelApiUtility
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * object class used in order to properly call the marvel API
 */
object MarvelApiClient {
    private const val API_KEY = BuildConfig.MARVEL_PUBLIC_KEY
    private const val PRIVATE_KEY = BuildConfig.MARVEL_PRIVATE_KEY

    /**
     * Function call to get a specific comic and its information by ID
     */
    fun getComic(comicId: Int, onResponse: (ComicResponse?) -> Unit, onFailure: (Throwable) -> Unit) {
        val service = RetrofitClient.marvelApiService

        val timeStamp = (System.currentTimeMillis() / 1000).toString()
        val hash = MarvelApiUtility.generateHash(timeStamp, PRIVATE_KEY, API_KEY)

        val call = service.getComic(comicId, API_KEY, timeStamp, hash)
        call.enqueue(object : Callback<ComicResponse> {
            override fun onResponse(call: Call<ComicResponse>, response: Response<ComicResponse>) {
                if (response.isSuccessful) {
                    onResponse(response.body())
                } else {
                    onFailure(Throwable("Failed to get comic"))
                }
            }

            override fun onFailure(call: Call<ComicResponse>, t: Throwable) {
                onFailure(t)
            }
        })
    }


}