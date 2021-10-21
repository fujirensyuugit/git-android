package com.example.mygoogleapi

import retrofit2.Retrofit

class SearchRepositryimpl {
     fun getGoogleAPI(query: String,retrofit: Retrofit) {
        val apiService = retrofit.create(ApiService::class.java)
        apiService.getSearch(query)
    }
}