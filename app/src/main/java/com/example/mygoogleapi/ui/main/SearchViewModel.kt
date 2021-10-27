package com.example.mygoogleapi.ui.main

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mygoogleapi.ApiService
import com.example.mygoogleapi.Kensakudata
import com.example.mygoogleapi.Searchrepositry
import com.google.gson.GsonBuilder
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import java.util.concurrent.TimeUnit
import retrofit2.converter.gson.GsonConverterFactory;

class SearchViewModel : ViewModel(),Searchrepositry {
    private var _searchResult: MutableLiveData<Result<Kensakudata>> = MutableLiveData()
    val searchResult: LiveData<Result<Kensakudata>>  = _searchResult
    private val GoogleBookUrl: String = "https://www.googleapis.com"

    private val httpClient: OkHttpClient = OkHttpClient.Builder()
        .connectTimeout(120, TimeUnit.SECONDS)
        .readTimeout(120, TimeUnit.SECONDS)
        .addInterceptor(HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        })
        .build()
    val gson = GsonBuilder()
        .create() //

    var retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(GoogleBookUrl)
        .client(httpClient)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()

    val apiService = retrofit.create(ApiService::class.java)

    @SuppressLint("CheckResult")
    fun getBookData(query:String) {
        apiService.getSearch(query)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
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




