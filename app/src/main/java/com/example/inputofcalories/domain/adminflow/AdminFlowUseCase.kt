package com.example.inputofcalories.domain.adminflow

import com.example.inputofcalories.entity.register.User

interface AdminFlowUseCase {
    suspend fun downgradeToManager(userId: String)

    suspend fun downgradeToRegular(userId: String)

    suspend fun upgradeToManager(userId: String)

    suspend fun upgradeToAdmin(userId: String)

    suspend fun getUsers(): List<User>
}