package com.example.inputofcalories.repo.adminflow

import com.example.inputofcalories.entity.register.User

interface AdminRepo {
    suspend fun downgradeToManager(userId: String)

    suspend fun downgradeToRegular(userId: String)

    suspend fun upgradeToAdmin(userId: String)

    suspend fun upgradeToManager(userId: String)

    suspend fun getUsers(userId: String): List<User>
}