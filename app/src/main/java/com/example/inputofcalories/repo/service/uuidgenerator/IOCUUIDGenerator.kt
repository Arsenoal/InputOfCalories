package com.example.inputofcalories.repo.service.uuidgenerator

import java.util.*

class IOCUUIDGenerator:
    UUIDGeneratorService {
    override suspend fun get() = UUID.randomUUID().toString()
}