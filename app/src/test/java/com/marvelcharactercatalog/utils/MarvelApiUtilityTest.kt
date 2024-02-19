package com.marvelcharactercatalog.utils

import org.junit.Test
import org.junit.jupiter.api.Assertions.*

class MarvelApiUtilityTest {

    @Test
    fun `convertToHttps converts HTTP URL to HTTPS`() {
        val httpUrl = "http://example.com"
        val expectedHttpsUrl = "https://example.com"
        assertEquals(expectedHttpsUrl, MarvelApiUtility.convertHttpToHttps(httpUrl))
    }

    @Test
    fun `convertToHttps keeps HTTPS URL unchanged`() {
        val httpsUrl = "https://example.com"
        assertEquals(httpsUrl, MarvelApiUtility.convertHttpToHttps(httpsUrl))
    }

    @Test
    fun `convertToHttps converts relative URL to HTTPS`() {
        val relativeUrl = "example.com"
        val expectedHttpsUrl = "https://example.com"
        assertEquals(expectedHttpsUrl, MarvelApiUtility.convertHttpToHttps(relativeUrl))
    }

    @Test
    fun `convertToHttps handles URL with parameters`() {
        val httpUrl = "http://example.com/page?param=value"
        val expectedHttpsUrl = "https://example.com/page?param=value"
        assertEquals(expectedHttpsUrl, MarvelApiUtility.convertHttpToHttps(httpUrl))
    }

    @Test
    fun `convertToHttps handles empty string`() {
        val emptyUrl = ""
        assertEquals(emptyUrl, MarvelApiUtility.convertHttpToHttps(emptyUrl))
    }

    @Test
    fun `convertToHttps keeps HTTPS URL with port unchanged`() {
        val httpsUrlWithPort = "https://example.com:8080"
        assertEquals(httpsUrlWithPort, MarvelApiUtility.convertHttpToHttps(httpsUrlWithPort))
    }

    @Test
    fun `convertToHttps handles URL with paths`() {
        val httpUrlWithPath = "http://example.com/path/to/resource"
        val expectedHttpsUrl = "https://example.com/path/to/resource"
        assertEquals(expectedHttpsUrl, MarvelApiUtility.convertHttpToHttps(httpUrlWithPath))
    }

    @Test
    fun `convertToHttps handles URL with query string`() {
        val httpUrlWithQuery = "http://example.com/page?param1=value1&param2=value2"
        val expectedHttpsUrl = "https://example.com/page?param1=value1&param2=value2"
        assertEquals(expectedHttpsUrl, MarvelApiUtility.convertHttpToHttps(httpUrlWithQuery))
    }

    @Test
    fun `convertToHttps handles URL with hash fragment`() {
        val httpUrlWithHash = "http://example.com#section"
        val expectedHttpsUrl = "https://example.com#section"
        assertEquals(expectedHttpsUrl, MarvelApiUtility.convertHttpToHttps(httpUrlWithHash))
    }
    @Test
    fun `generateHash returns empty string if any input is empty`() {
        assertEquals("", MarvelApiUtility.generateHash("", "privateKey", "publicKey"))
        assertEquals("", MarvelApiUtility.generateHash("timeStamp", "", "publicKey"))
        assertEquals("", MarvelApiUtility.generateHash("timeStamp", "privateKey", ""))
        assertEquals("", MarvelApiUtility.generateHash("", "", ""))
    }

    @Test
    fun `generateHash returns correct hash for valid inputs`() {
        val expectedHash = "05327b12d0fa92bdb450b2848e9dc2b9" // Hash of "testprivatepublic"
        val actualHash = MarvelApiUtility.generateHash("test", "private", "public")
        assertEquals(expectedHash, actualHash)
    }

    @Test
    fun `generateHash returns consistent hash for same inputs`() {
        val timeStamp = "testTime"
        val privateKey = "testPrivate"
        val publicKey = "testPublic"

        val hash1 = MarvelApiUtility.generateHash(timeStamp, privateKey, publicKey)
        val hash2 = MarvelApiUtility.generateHash(timeStamp, privateKey, publicKey)

        assertEquals(hash1, hash2)
    }

    @Test
    fun `generateHash handles edge case of very long inputs`() {
        // Generate very long inputs
        val timeStamp = "a".repeat(100000)
        val privateKey = "b".repeat(100000)
        val publicKey = "c".repeat(100000)

        // Ensure the function does not throw an exception
        MarvelApiUtility.generateHash(timeStamp, privateKey, publicKey)
    }

    @Test
    fun `generateHash handles edge case of special characters in inputs`() {
        // Test with inputs containing special characters
        val timeStamp = "!@#$%^&*()"
        val privateKey = "<>?/.,;:'\""
        val publicKey = "{}[]-_=+"

        // Ensure the function does not throw an exception
        MarvelApiUtility.generateHash(timeStamp, privateKey, publicKey)
    }
}