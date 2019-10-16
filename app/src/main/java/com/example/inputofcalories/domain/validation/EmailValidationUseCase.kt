package com.example.inputofcalories.domain.validation

import io.reactivex.Single

interface EmailValidationUseCase {
    fun validate(): Single<Boolean>
}