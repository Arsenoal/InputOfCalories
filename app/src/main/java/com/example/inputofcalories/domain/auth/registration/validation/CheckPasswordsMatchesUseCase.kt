package com.example.inputofcalories.domain.auth.registration.validation

import io.reactivex.Single

interface CheckPasswordsMatchesUseCase {
    fun check(p1: String, p2: String): Single<Boolean>
}