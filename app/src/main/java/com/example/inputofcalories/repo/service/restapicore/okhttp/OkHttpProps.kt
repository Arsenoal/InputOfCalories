package com.example.inputofcalories.repo.service.restapicore.okhttp

import org.koin.core.qualifier.named

object OkHttpProps {
    const val CACHE_SIZE = 10 * 1024 * 1024L

    const val CONNECTION_TIMEOUT = 10L
    const val CONNECTION_LONG_TIMEOUT = 20L
    const val READ_WRITE_TIMEOUT = 10L

    val DEFAULT_CLIENT = named("default_client")
    val CF_CLIENT = named("cf_client")
}