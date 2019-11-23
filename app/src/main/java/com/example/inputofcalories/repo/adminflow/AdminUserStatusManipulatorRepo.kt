package com.example.inputofcalories.repo.adminflow

interface AdminUserStatusManipulatorRepo {
    suspend fun downgradeToManager(userId: String)

    suspend fun downgradeToRegular(userId: String)

    suspend fun upgradeToAdmin(userId: String)

    suspend fun upgradeToManager(userId: String)
}