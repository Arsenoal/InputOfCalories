package com.example.inputofcalories.domain.auth.validation

interface CheckEmailFormatValidUseCase {
    suspend fun check(email: String): Boolean
}