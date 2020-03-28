package com.example.inputofcalories.domain.auth

import com.example.inputofcalories.entity.register.User
import com.example.inputofcalories.entity.register.UserRegistrationParams
import com.example.inputofcalories.entity.register.UserSignInParams

interface AuthUseCase {
    suspend fun register(userRegistrationParams: UserRegistrationParams, result: (Any) -> Unit)

    suspend fun signIn(userSignInParams: UserSignInParams): User
}