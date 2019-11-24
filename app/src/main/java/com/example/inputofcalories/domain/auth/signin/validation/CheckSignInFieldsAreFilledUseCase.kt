package com.example.inputofcalories.domain.auth.signin.validation

import com.example.inputofcalories.entity.register.UserSignInParams

interface CheckSignInFieldsAreFilledUseCase {
    suspend fun check(userSignInParams: UserSignInParams): Boolean
}