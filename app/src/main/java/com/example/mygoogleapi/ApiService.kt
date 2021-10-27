package com.example.mygoogleapi

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("books/v1/volumes")
    fun getSearch(@Query("q") query: String): Single<Kensakudata>
}