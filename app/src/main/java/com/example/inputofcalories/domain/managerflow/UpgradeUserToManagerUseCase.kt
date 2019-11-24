package com.example.inputofcalories.domain.managerflow

interface UpgradeUserToManagerUseCase {
    suspend fun upgrade(userId: String)
}