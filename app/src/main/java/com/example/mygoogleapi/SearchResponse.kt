package com.example.mygoogleapi


data class Kensakudata(val volumeInfo:List<VolumeInfo>,  val imageLinks:List<ImageLinks>)

data class VolumeInfo(
    val title:String = "",
    val subtitle:String= "",
    val autor:List<String>
)

data class ImageLinks(
    val smallThumbnail:String,
    val thumbnail:String,
    val small:String,
    val medium:String,
    val large:String,
    val extraLarge:String
    )