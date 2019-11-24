package com.example.inputofcalories.domain.validation

interface EmailValidationUseCase {
    suspend fun validate(): Boolean
}