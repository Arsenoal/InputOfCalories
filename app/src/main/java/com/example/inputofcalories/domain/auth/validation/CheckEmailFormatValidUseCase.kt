package com.example.inputofcalories.domain.auth.validation

import io.reactivex.Single

interface CheckEmailFormatValidUseCase {
    fun check(email: String): Single<Boolean>
}