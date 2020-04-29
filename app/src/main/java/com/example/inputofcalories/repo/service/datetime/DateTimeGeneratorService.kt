package com.example.inputofcalories.repo.service.datetime

interface DateTimeGeneratorService {
    suspend fun get(): String
}