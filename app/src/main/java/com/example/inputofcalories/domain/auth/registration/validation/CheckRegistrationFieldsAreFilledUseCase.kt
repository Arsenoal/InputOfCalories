package com.example.inputofcalories.domain.auth.registration.validation

import com.example.inputofcalories.entity.register.UserRegistrationParams

interface CheckRegistrationFieldsAreFilledUseCase {
    suspend fun check(userRegistrationParams: UserRegistrationParams): Boolean
}