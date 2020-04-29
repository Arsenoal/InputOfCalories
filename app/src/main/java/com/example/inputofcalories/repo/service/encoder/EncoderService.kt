package com.example.inputofcalories.repo.service.encoder

interface EncoderService {
    suspend fun encode(text: String): String
}