package com.example.inputofcalories.domain.validation

import io.reactivex.Single

interface UserNameValidationUseCase {
    fun validate(): Single<Boolean>
}