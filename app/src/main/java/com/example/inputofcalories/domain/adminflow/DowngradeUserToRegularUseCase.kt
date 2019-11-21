package com.example.inputofcalories.domain.adminflow

interface DowngradeUserToRegularUseCase {
    suspend fun downgrade(userId: String)
}