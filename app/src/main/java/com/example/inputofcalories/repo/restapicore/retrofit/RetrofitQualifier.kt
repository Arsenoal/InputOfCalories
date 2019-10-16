package com.example.inputofcalories.repo.restapicore.retrofit

import org.koin.core.qualifier.named

object RetrofitQualifier {
    val DEFAULT_INSTANCE = named("retrofit_default_instance")
}