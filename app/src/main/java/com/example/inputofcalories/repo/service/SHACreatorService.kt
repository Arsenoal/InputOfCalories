package com.example.inputofcalories.repo.service

interface SHACreatorService {
    suspend fun encrypt(text: String): String
}