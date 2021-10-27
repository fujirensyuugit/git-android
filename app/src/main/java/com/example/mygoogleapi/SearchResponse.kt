package com.example.mygoogleapi


data class Kensakudata(
    val items: List<items>? = null
)

data class items(
    val id: String = "",
    val volumeInfo: volumeInfo,
    val ImageLinks:ImageLinks
)

data class volumeInfo(
    val title: String = "",
    val subtitle: String = "",
    val autor: List<String>? = null
)

data class ImageLinks(
    val smallThumbnail: String,
    val thumbnail: String,
    val small: String,
    val medium: String,
    val large: String,
    val extraLarge: String
)