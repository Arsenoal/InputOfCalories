package com.example.inputofcalories.repo.restapicore

import com.example.inputofcalories.repo.restapicore.builder.ClientBuilder
import com.example.inputofcalories.repo.restapicore.builder.DefaultBuilder
import com.example.inputofcalories.repo.restapicore.gson.gsonModule
import com.example.inputofcalories.repo.restapicore.okhttp.okHttpModule
import com.example.inputofcalories.repo.restapicore.retrofit.RetrofitQualifier
import com.example.inputofcalories.repo.restapicore.retrofit.retrofitModule
import org.koin.core.KoinComponent
import org.koin.core.context.loadKoinModules
import org.koin.core.inject
import retrofit2.Retrofit

typealias LazyInitHeadersBlock = () -> Map<String, String>

data class ApiProviderConfig(
    val baseUrl: String,
    val lazyInitHeadersBlock: LazyInitHeadersBlock = { mapOf() },
    val isDebuggableMode: Boolean,
    val clientBuilder: ClientBuilder = DefaultBuilder()
)

class RestApiCreator(providerConfig: ApiProviderConfig) : KoinComponent {

    private val defaultRetrofit: Retrofit by inject(RetrofitQualifier.DEFAULT_INSTANCE)

    init {
        loadKoinModules(listOf(gsonModule(), okHttpModule(providerConfig), retrofitModule(providerConfig)))
    }

    @JvmOverloads
    fun <API_SERVICE_INTERFACE> createApiService(apiClass: Class<API_SERVICE_INTERFACE>,
                                                 clientConfigType: ClientConfigType = DefaultConfigType): API_SERVICE_INTERFACE {

        return getRetrofitByType(clientConfigType).create(apiClass)
    }

    private fun getRetrofitByType(type: ClientConfigType) = when (type) {
        DefaultConfigType -> defaultRetrofit
    }

}
