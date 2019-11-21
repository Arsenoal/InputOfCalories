package com.example.inputofcalories.domain.adminflow

interface UpgradeUserToManagerUseCase {
    suspend fun upgrade(userId: String)
}