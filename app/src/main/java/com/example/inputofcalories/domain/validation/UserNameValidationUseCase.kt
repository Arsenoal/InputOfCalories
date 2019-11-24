package com.example.inputofcalories.domain.validation

interface UserNameValidationUseCase {
    suspend fun validate(): Boolean
}