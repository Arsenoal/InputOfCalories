package com.example.inputofcalories.repo.service

interface UUIDGeneratorService {
    suspend fun get(): String
}