package com.example.inputofcalories.repo.restapicore.builder

import okhttp3.OkHttpClient
import retrofit2.Retrofit

class DefaultBuilder: ClientBuilder {
    override fun buildOkHttpClient(okHttpClient: OkHttpClient): OkHttpClient = okHttpClient

    override fun buildRetrofitClient(retrofitClient: Retrofit): Retrofit = retrofitClient
}