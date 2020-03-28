package com.example.inputofcalories.repo.auth

import com.example.inputofcalories.entity.register.User
import com.example.inputofcalories.entity.register.UserRegistrationParams

interface AuthRepo {
    suspend fun register(userRegistrationParams: UserRegistrationParams)

    suspend fun signIn(email: String): User?
}