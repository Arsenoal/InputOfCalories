package com.example.inputofcalories.repo.service.datetime

import java.text.SimpleDateFormat
import java.util.*

private const val PATTERN = "yyyy/MMMM/dd"
const val YEAR = 0
const val MONTH = 1
const val DAY = 2

class IOCDateTimeGenerator: DateTimeGeneratorService {
    override suspend fun getByDate(date: Date): Map<Int, String> = with(SimpleDateFormat(PATTERN, Locale.ENGLISH)) {
        mutableMapOf<Int, String>().run {
            format(date).split("/").let { list ->
                put(YEAR, list[0])
                put(MONTH, list[1])
                put(DAY, list[2])
            }

            this
        }
    }

    override suspend fun getAsPath(date: Date): String = with(SimpleDateFormat(PATTERN, Locale.ENGLISH)) {
        format(date)
    }
}