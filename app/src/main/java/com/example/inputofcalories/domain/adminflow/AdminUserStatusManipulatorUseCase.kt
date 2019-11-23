package com.example.inputofcalories.domain.adminflow

interface AdminUserStatusManipulatorUseCase {
    suspend fun downgradeToManager(userId: String)

    suspend fun downgradeToRegular(userId: String)

    suspend fun upgradeToManager(userId: String)

    suspend fun upgradeToAdmin(userId: String)
}