package com.example.inputofcalories.repo.common.service

import java.util.*

class UUIDGeneratorServiceImpl: UUIDGeneratorService {
    override suspend fun get(): UUID = UUID.randomUUID()
}