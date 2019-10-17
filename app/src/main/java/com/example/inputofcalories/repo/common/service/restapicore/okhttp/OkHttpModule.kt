package com.example.inputofcalories.repo.common.service.restapicore.okhttp

import com.example.inputofcalories.repo.common.service.restapicore.ApiProviderConfig
import com.example.inputofcalories.repo.common.service.restapicore.interceptor.HeaderInterceptor
import com.example.inputofcalories.repo.common.service.restapicore.retrofit.OkHttpConfig
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.parameter.parametersOf
import org.koin.dsl.module
import java.util.concurrent.TimeUnit

fun okHttpModule(apiProviderConfig: ApiProviderConfig) = module {

    single {
        OkHttpClient()
    }

    factory<OkHttpClient.Builder> { (config: OkHttpConfig) ->

        get<OkHttpClient>().newBuilder().apply {
            connectTimeout(config.connectionTimeout, TimeUnit.SECONDS)
            readTimeout(config.readAndWriteTimeout, TimeUnit.SECONDS)
            writeTimeout(config.readAndWriteTimeout, TimeUnit.SECONDS)
            val interceptors: List<Interceptor> = get()
            interceptors.forEach { interceptor ->
                addInterceptor(interceptor)
            }

            if (config.protocols.isNotEmpty()) protocols(config.protocols)

            if (config.cacheFile != null) cache(Cache(config.cacheFile, OkHttpProps.CACHE_SIZE))

            if (config.interceptors.isNotEmpty()) config.interceptors.forEach { interceptor -> addInterceptor(interceptor) }
        }
    }

    factory(OkHttpProps.DEFAULT_CLIENT) { (config: OkHttpConfig) ->
        apiProviderConfig.clientBuilder.buildOkHttpClient(get<OkHttpClient.Builder> { parametersOf(config) }.build())
    }

    factory {
        val interceptors = mutableListOf<Interceptor>()
        if (apiProviderConfig.isDebuggableMode) {

            val loggingHeader = HttpLoggingInterceptor()
            loggingHeader.level = HttpLoggingInterceptor.Level.HEADERS
            interceptors.add(loggingHeader)

            val loggingBody = HttpLoggingInterceptor()
            loggingBody.level = HttpLoggingInterceptor.Level.BODY
            interceptors.add(loggingBody)
        }

        if (apiProviderConfig.lazyInitHeadersBlock().isNotEmpty()) {
            interceptors.add(HeaderInterceptor(apiProviderConfig.lazyInitHeadersBlock))
        }

        interceptors.toList()
    }

}