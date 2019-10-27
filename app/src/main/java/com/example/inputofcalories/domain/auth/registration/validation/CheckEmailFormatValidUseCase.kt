package com.example.inputofcalories.domain.auth.registration.validation

import io.reactivex.Single

interface CheckEmailFormatValidUseCase {
    fun check(email: String): Single<Boolean>
}