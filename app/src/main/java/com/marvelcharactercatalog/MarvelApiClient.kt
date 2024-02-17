package com.marvelcharactercatalog

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.math.BigInteger
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException

object MarvelApiClient {
    private const val API_KEY = "8cd5874a989ff147655cff49907103b9" // Replace with your actual API key
    //TODO: move the private key into a keystore or somewhere else secure. Just using this for testing
    private const val PRIVATE_KEY = "3cd2c8c0ad58ec2b2806aaa92b361b232e693df3" // Replace with your actual private key

    fun getComic(comicId: Int, onResponse: (ComicResponse?) -> Unit, onFailure: (Throwable) -> Unit) {
        val service = RetrofitClient.marvelApiService

        val timeStamp = (System.currentTimeMillis() / 1000).toString()
        val hash = generateHash(timeStamp, PRIVATE_KEY, API_KEY)

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

    private fun generateHash(timeStamp: String, privateKey: String, publicKey: String): String {
        val input = timeStamp + privateKey + publicKey
        return try {
            val md = MessageDigest.getInstance("MD5")
            val messageDigest = md.digest(input.toByteArray())
            val no = BigInteger(1, messageDigest)
            var hashText = no.toString(16)
            while (hashText.length < 32) {
                hashText = "0$hashText"
            }
            hashText
        } catch (e: NoSuchAlgorithmException) {
            throw RuntimeException(e)
        }
    }
}