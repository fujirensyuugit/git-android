package com.example.mygoogleapi.ui.main

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import androidx.lifecycle.ViewModel
import com.example.mygoogleapi.ApiService
import com.example.mygoogleapi.Kensakudata
import com.example.mygoogleapi.SearchRepositryimpl
import com.example.mygoogleapi.Searchrepositry
import com.squareup.moshi.Moshi
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

class SearchViewModel : ViewModel(),Searchrepositry {
    private var _searchResult: MutableLiveData<Result<Kensakudata>> = MutableLiveData()
    val searchResult: LiveData<Result<Kensakudata>> get() = _searchResult
    private val GoogleBookUrl: String = "https://www.googleapis.com"

    private val client: OkHttpClient = OkHttpClient.Builder()
        .connectTimeout(120, TimeUnit.SECONDS)
        .readTimeout(120, TimeUnit.SECONDS)
        .addInterceptor(HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        })
        .build()

    private val moshi: Moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    var retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(GoogleBookUrl)
        .client(client)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()

    val apiService = retrofit.create(ApiService::class.java)

    @SuppressLint("CheckResult")
    fun getBookData(query:String) {
        apiService.getSearch(query)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.newThread())

            .subscribe( {
                _searchResult.postValue(Result.success(it))
            }, {
                _searchResult.postValue(Result.failure(it))
            })
    }

    override fun getGoogleAPI(query: String, retrofit: Retrofit) {
        val apiService = retrofit.create(ApiService::class.java)
        apiService.getSearch(query)
    }
}




