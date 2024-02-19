package com.marvelcharactercatalog.utils

import java.math.BigInteger
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException

object MarvelApiUtility {
    fun generateHash(timeStamp: String, privateKey: String, publicKey: String): String {
        if (timeStamp.isEmpty() || privateKey.isEmpty() || publicKey.isEmpty()) {
            return ""
        }
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

     fun convertHttpToHttps(httpUrl: String): String {
        val httpsPrefix = "https://"
        val httpPrefix = "http://"
         if (httpUrl.isEmpty()) {
             return ""
         }

        return if (httpUrl.startsWith(httpPrefix)) {
            // Replace 'http://' with 'https://'
            httpsPrefix + httpUrl.substring(httpPrefix.length)
        } else {
            // If the URL already starts with 'https://', return it as is
            if (httpUrl.startsWith(httpsPrefix)) {
                httpUrl
            } else {
                // If the URL does not start with either 'http://' or 'https://', assume it's a relative URL and prepend 'https://'
                httpsPrefix + httpUrl
            }
        }
    }
}