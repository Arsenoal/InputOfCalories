package com.example.inputofcalories.domain.adminflow

import com.example.inputofcalories.entity.register.User

interface AdminFlowUseCase {
    suspend fun changeUserType(userId: String, newType: Int)

    suspend fun getUsers(): List<User>
}