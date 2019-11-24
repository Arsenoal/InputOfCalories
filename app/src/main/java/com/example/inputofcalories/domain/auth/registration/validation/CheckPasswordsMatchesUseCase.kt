package com.example.inputofcalories.domain.auth.registration.validation

interface CheckPasswordsMatchesUseCase {
    suspend fun check(p1: String, p2: String): Boolean
}