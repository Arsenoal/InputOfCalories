package com.example.inputofcalories.repo.restapicore.builder

import okhttp3.OkHttpClient
import retrofit2.Retrofit

interface ClientBuilder {
    fun buildOkHttpClient(okHttpClient: OkHttpClient): OkHttpClient

    fun buildRetrofitClient(retrofitClient: Retrofit): Retrofit
}