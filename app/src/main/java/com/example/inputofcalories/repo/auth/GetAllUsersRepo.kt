package com.example.inputofcalories.repo.auth

import com.example.inputofcalories.entity.register.User

interface GetAllUsersRepo {
    suspend fun get(): List<User>
}