package com.example.inputofcalories.repo.user

import com.example.inputofcalories.entity.register.User

interface UserRepo {
    suspend fun get(): User

    suspend fun set(user: User)
}