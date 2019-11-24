package com.example.inputofcalories.domain.auth.signin

import com.example.inputofcalories.entity.register.User
import com.example.inputofcalories.entity.register.UserSignInParams

interface SignInUserUseCase {
    suspend fun signIn(userSignInParams: UserSignInParams): User
}