package com.example.inputofcalories.repo.service.uuidgenerator

interface UUIDGeneratorService {
    suspend fun get(): String
}