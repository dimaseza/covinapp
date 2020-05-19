package com.daivers.covinapp.network

import com.daivers.covinapp.model.NewsItemNetwork
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Deferred
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApiService {
    @GET("top-headlines")
    fun getTopHeadlineNewsAsync(
        @Query("country") country: String,
        @Query("apiKey") apiKey: String,
        @Query("category") category: String,
        @Query("pageSize") pageSize: Int = 3
    ) : Deferred<NewsItemNetwork>

    @GET("top-headlines")
    fun getTodayNewsAsync(
        @Query("country") country: String,
        @Query("apiKey") apiKey: String,
        @Query("pageSize") pageSize: Int = 3
    ): Deferred<NewsItemNetwork>

    @GET("top-headlines")
        fun getAllNewsAsync(
        @Query("country") country: String,
        @Query("apiKey") apiKey: String,
        @Query("category") category: String
    ) : Deferred<NewsItemNetwork>
}

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

object NewsNetwork {
    private val retrofitNews = Retrofit.Builder()
        .baseUrl("http://newsapi.org/v2/")
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .build()

    val NEWS: NewsApiService = retrofitNews.create(NewsApiService::class.java)
}