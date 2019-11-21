package com.example.inputofcalories.domain.adminflow

import com.example.inputofcalories.entity.register.User

interface GetAllUsersUseCase {
    suspend fun get(userId: String): List<User>
}