package com.example.mygoogleapi

import retrofit2.Retrofit

interface Searchrepositry {
    fun getGoogleAPI(query:String,retrofit:Retrofit)
}