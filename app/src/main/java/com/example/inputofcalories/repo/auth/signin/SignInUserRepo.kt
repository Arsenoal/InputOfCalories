package com.example.inputofcalories.repo.auth.signin

import com.example.inputofcalories.entity.register.User
import com.example.inputofcalories.entity.register.UserSignInParams

interface SignInUserRepo {
    suspend fun signIn(userSignInParams: UserSignInParams): User
}