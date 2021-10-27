package com.example.mygoogleapi



data class Kensakudata(
    val items:List<items>
)

data class items(
    val id:String = "",
    val volumeInfo:volumeInfo
)

data class volumeInfo(
    val title:String = "",
    val subtitle:String= "",
    val autor:List<String> = listOf<String>("")
)

data class ImageLinks(
    val smallThumbnail:String,
    val thumbnail:String,
    val small:String,
    val medium:String,
    val large:String,
    val extraLarge:String
    )