package com.example.inputofcalories.repo.managerflow

interface DowngradeManagerToRegularUserRepo {
    suspend fun downgrade(userId: String)
}