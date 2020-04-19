package com.example.inputofcalories.repo.service

import java.util.*

class IOCUUIDGenerator: UUIDGeneratorService {
    override suspend fun get() = UUID.randomUUID().toString()
}