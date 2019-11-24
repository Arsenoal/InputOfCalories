package com.example.inputofcalories.repo.common.service

import java.util.*

interface UUIDGeneratorService {
    suspend fun get(): UUID
}