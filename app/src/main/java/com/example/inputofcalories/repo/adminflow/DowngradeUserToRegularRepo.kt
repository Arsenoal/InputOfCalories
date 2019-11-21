package com.example.inputofcalories.repo.adminflow

interface DowngradeUserToRegularRepo {
    suspend fun downgrade(userId: String)
}