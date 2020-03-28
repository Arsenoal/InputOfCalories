package com.example.inputofcalories.repo.service

interface EncoderService {
    suspend fun encode(text: String): String
}