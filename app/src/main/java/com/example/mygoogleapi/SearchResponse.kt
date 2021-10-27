package com.example.mygoogleapi

import com.google.gson.annotations.SerializedName


data class Kensakudata(

    val items: List<items>? = null
)

data class items(
    val id: String = "",
    val volumeInfo: volumeInfo
)

data class volumeInfo(
    val title: String = "",
    val subtitle: String = "",
    @SerializedName("authors")
    val authors: List<String>? = null,
    @SerializedName("imageLinks")
    val ImageLinks:ImageLinks? = null
)

data class ImageLinks(
    val smallThumbnail: String,
    val thumbnail: String,
    val small: String,
    val medium: String,
    val large: String,
    val extraLarge: String
)