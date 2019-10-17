package com.example.inputofcalories.repo.common.service.restapicore.retrofit

import com.example.inputofcalories.repo.common.service.restapicore.ApiProviderConfig
import com.example.inputofcalories.repo.common.service.restapicore.gson.GsonQualifier
import com.example.inputofcalories.repo.common.service.restapicore.okhttp.OkHttpProps
import com.google.gson.Gson
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Protocol
import org.koin.core.parameter.parametersOf
import org.koin.core.scope.Scope
import org.koin.dsl.bind
import org.koin.dsl.module
import retrofit2.CallAdapter
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File

internal fun Scope.createOkHttpClient(config: OkHttpConfig = DefaultOkHttpConfig()) = get<OkHttpClient>(OkHttpProps.DEFAULT_CLIENT) { parametersOf(config) }

private typealias DefaultOkHttpConfig = OkHttpConfig

internal data class OkHttpConfig(val connectionTimeout: Long = OkHttpProps.CONNECTION_TIMEOUT,
                                 val readAndWriteTimeout: Long = OkHttpProps.READ_WRITE_TIMEOUT,
                                 val protocols: List<Protocol> = listOf(),
                                 val interceptors: List<Interceptor> = listOf(),
                                 val cacheFile: File? = null)


fun retrofitModule(apiProviderConfig: ApiProviderConfig) = module {
    single { GsonConverterFactory.create(get<Gson>(GsonQualifier.DEFAULT)) } bind Converter.Factory::class

    single { RxJava2CallAdapterFactory.create() } bind CallAdapter.Factory::class

    single {
        Retrofit.Builder().apply {
            baseUrl(apiProviderConfig.baseUrl)
            addConverterFactory(get())
            addCallAdapterFactory(get())
        }
    }

    factory(RetrofitQualifier.DEFAULT_INSTANCE) {
        apiProviderConfig
            .clientBuilder
            .buildRetrofitClient(get<Retrofit.Builder>().client(createOkHttpClient()).build())
    }
}