package com.example.inputofcalories.repo.service.datetime

import java.util.Date

interface DateTimeGeneratorService {
    suspend fun getByDate(date: Date): Map<Int, String>

    suspend fun getAsPath(date: Date): String
}