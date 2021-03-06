package com.example.inputofcalories.domain.managerflow

import com.example.inputofcalories.entity.register.User

interface MangerFlowUseCase {
    suspend fun downgradeToRegular(userId: String)

    suspend fun getUsers(): List<User>

    suspend fun upgradeToManager(userId: String)
}