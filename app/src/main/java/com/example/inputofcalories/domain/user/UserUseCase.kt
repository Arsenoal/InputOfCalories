package com.example.inputofcalories.domain.user

import com.example.inputofcalories.entity.register.User

interface UserUseCase {
    suspend fun get(): User

    suspend fun set(user: User)
}