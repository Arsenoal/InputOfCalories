package com.example.inputofcalories.domain.user

import com.example.inputofcalories.entity.register.User

interface GetUserUseCase {
    suspend fun get(): User
}