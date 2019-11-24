package com.example.inputofcalories.domain.auth.registration.validation

class CheckPasswordsMatchesUseCaseImpl: CheckPasswordsMatchesUseCase {
    override suspend fun check(p1: String, p2: String): Boolean {
        return p1 == p2
    }
}