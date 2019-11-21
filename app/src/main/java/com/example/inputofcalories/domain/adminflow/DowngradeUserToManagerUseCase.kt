package com.example.inputofcalories.domain.adminflow

interface DowngradeUserToManagerUseCase {
    suspend fun downgrade(userId: String)
}