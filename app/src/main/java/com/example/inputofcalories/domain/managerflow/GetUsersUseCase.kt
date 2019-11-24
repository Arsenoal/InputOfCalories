package com.example.inputofcalories.domain.managerflow

import com.example.inputofcalories.entity.register.User

interface GetUsersUseCase {
    suspend fun get(userId: String): List<User>
}