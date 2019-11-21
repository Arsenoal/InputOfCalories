package com.example.inputofcalories.domain.adminflow

interface UpgradeUserToAdminUseCase {
    suspend fun upgrade(userId: String)
}