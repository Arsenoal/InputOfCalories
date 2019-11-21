package com.example.inputofcalories.repo.adminflow

import com.example.inputofcalories.entity.register.User

interface GetAllUsersRepo {
    suspend fun get(userId: String): List<User>
}