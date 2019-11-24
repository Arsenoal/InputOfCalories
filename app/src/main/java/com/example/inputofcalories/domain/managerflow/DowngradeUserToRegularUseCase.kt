package com.example.inputofcalories.domain.managerflow

interface DowngradeUserToRegularUseCase {
    suspend fun downgrade(userId: String)
}