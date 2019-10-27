package com.example.inputofcalories.domain.auth.registration.validation

import com.example.inputofcalories.entity.register.UserRegistrationParams
import io.reactivex.Single

interface CheckRegistrationFieldsAreFilledUseCase {
    fun check(userRegistrationParams: UserRegistrationParams): Single<Boolean>
}