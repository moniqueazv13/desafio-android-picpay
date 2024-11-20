package com.picpay.desafio.android.di

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory


val securityModule = module {

    factory { Moshi.Builder().add(KotlinJsonAdapterFactory()).build() }

    factory { OkHttpClient.Builder().build() }

    factory {
        val moshi: Moshi = get()
        val client: OkHttpClient = get()
        Retrofit.Builder()
            .baseUrl("https://609a908e0f5a13001721b74e.mockapi.io/picpay/api/")
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .client(client)
            .build()
    }
}