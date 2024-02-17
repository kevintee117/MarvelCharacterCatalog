package com.marvelcharactercatalog

import com.google.gson.annotations.SerializedName
import java.util.Date

data class ComicResponse(
    @SerializedName("code") val code: Int,
    @SerializedName("status") val status: String,
    @SerializedName("copyright") val copyright: String,
    @SerializedName("attributionText") val attributionText: String,
    @SerializedName("attributionHTML") val attributionHTML: String,
    @SerializedName("data") val data: ComicData,
    @SerializedName("etag") val etag: String
)

data class ComicData(
    @SerializedName("offset") val offset: Int,
    @SerializedName("limit") val limit: Int,
    @SerializedName("total") val total: Int,
    @SerializedName("count") val count: Int,
    @SerializedName("results") val results: List<Comic>
)

data class Comic(
    @SerializedName("id") val id: Int,
    @SerializedName("digitalId") val digitalId: Int,
    @SerializedName("title") val title: String,
    @SerializedName("issueNumber") val issueNumber: Double,
    @SerializedName("variantDescription") val variantDescription: String,
    @SerializedName("description") val description: String,
    @SerializedName("modified") val modified: String,
    @SerializedName("isbn") val isbn: String,
    @SerializedName("upc") val upc: String,
    @SerializedName("diamondCode") val diamondCode: String,
    @SerializedName("ean") val ean: String,
    @SerializedName("issn") val issn: String,
    @SerializedName("format") val format: String,
    @SerializedName("pageCount") val pageCount: Int,
    @SerializedName("textObjects") val textObjects: List<TextObject>,
    @SerializedName("resourceURI") val resourceURI: String,
    @SerializedName("urls") val urls: List<Url>,
    @SerializedName("series") val series: Series,
    @SerializedName("variants") val variants: List<Variant>,
    @SerializedName("collections") val collections: List<Collection>,
    @SerializedName("collectedIssues") val collectedIssues: List<CollectedIssue>,
    @SerializedName("dates") val dates: List<DateItem>,
    @SerializedName("prices") val prices: List<Price>,
    @SerializedName("thumbnail") val thumbnail: Thumbnail,
    @SerializedName("images") val images: List<Image>,
    @SerializedName("creators") val creators: CreditList,
    @SerializedName("characters") val characters: CreditList,
    @SerializedName("stories") val stories: CreditList,
    @SerializedName("events") val events: CreditList
)

data class TextObject(
    @SerializedName("type") val type: String,
    @SerializedName("language") val language: String,
    @SerializedName("text") val text: String
)

data class Url(
    @SerializedName("type") val type: String,
    @SerializedName("url") val url: String
)

data class Series(
    @SerializedName("resourceURI") val resourceURI: String,
    @SerializedName("name") val name: String
)

data class Variant(
    @SerializedName("resourceURI") val resourceURI: String,
    @SerializedName("name") val name: String
)

data class Collection(
    @SerializedName("resourceURI") val resourceURI: String,
    @SerializedName("name") val name: String
)

data class CollectedIssue(
    @SerializedName("resourceURI") val resourceURI: String,
    @SerializedName("name") val name: String
)

data class DateItem(
    @SerializedName("type") val type: String,
    @SerializedName("date") val date: String
)

data class Price(
    @SerializedName("type") val type: String,
    @SerializedName("price") val price: Float
)

data class Thumbnail(
    @SerializedName("path") val path: String,
    @SerializedName("extension") val extension: String
)

data class Image(
    @SerializedName("path") val path: String,
    @SerializedName("extension") val extension: String
)

data class CreditList(
    @SerializedName("available") val available: Int,
    @SerializedName("returned") val returned: Int,
    @SerializedName("collectionURI") val collectionURI: String,
    @SerializedName("items") val items: List<Credit>
)

data class Credit(
    @SerializedName("resourceURI") val resourceURI: String,
    @SerializedName("name") val name: String,
    @SerializedName("role") val role: String
)
