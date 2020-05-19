package com.daivers.covinapp.network

import com.daivers.covinapp.model.KawalCoronaNetwork
import com.daivers.covinapp.model.NationCase
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Deferred
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

interface KawalCovidService{
    @GET("indonesia")
    fun getCaseIndonesiaAsync() : Deferred<List<NationCase>>
}

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

object KawalCovidApi {
    private val retrofitCovid = Retrofit.Builder()
        .baseUrl("https://api.kawalcorona.com/")
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .build()

    val COVID: KawalCovidService = retrofitCovid.create(KawalCovidService::class.java)
}
