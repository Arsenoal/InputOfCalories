package com.example.inputofcalories.repo.managerflow

import com.example.inputofcalories.entity.register.User

interface ManagerRepo {
    suspend fun downgradeToRegular(userId: String)

    suspend fun upgradeToManager(userId: String)

    suspend fun getUsers(userId: String): List<User>
}