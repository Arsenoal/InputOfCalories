package com.example.inputofcalories.repo.adminflow

interface DowngradeUserToManagerRepo {
    suspend fun downgrade(userId: String)
}