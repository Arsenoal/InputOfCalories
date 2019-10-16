package com.example.inputofcalories.repo.restapicore.interceptor

import com.example.inputofcalories.repo.restapicore.LazyInitHeadersBlock
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

class HeaderInterceptor(private val lazyInitHeadersBlock: LazyInitHeadersBlock = { mapOf() }) :
    Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val newBuilder = chain.request().newBuilder()
        val headers = lazyInitHeadersBlock()
        for ((k, v) in headers) {
            newBuilder.addHeader(k, v)
        }
        return chain.proceed(newBuilder.build())
    }
}
