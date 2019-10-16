package com.example.inputofcalories.repo.restapicore.gson

import com.google.gson.FieldNamingPolicy
import com.google.gson.GsonBuilder
import com.google.gson.JsonSerializer
import org.koin.dsl.module

const val DEFAULT_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"

fun gsonModule() = module {
    single(GsonQualifier.DEFAULT) {
        GsonBuilder()
            .setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE)
            .setDateFormat(DEFAULT_DATE_FORMAT)
            .serializeSpecialFloatingPointValues()
            .disableHtmlEscaping()
            .registerTypeAdapter(Double::class.java, get<JsonSerializer<Double>>())
            .create()
    }
}