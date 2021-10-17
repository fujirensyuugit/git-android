package com.example.mygoogleapi

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService{
    @GET("books/v1/volumes")
    fun getSearch(@Query("query") query: String): Call<Kensakudata>
}