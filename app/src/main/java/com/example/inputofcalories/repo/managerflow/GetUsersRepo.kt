package com.example.inputofcalories.repo.managerflow

import com.example.inputofcalories.entity.register.User

interface GetUsersRepo {
    suspend fun get(userId: String): List<User>
}