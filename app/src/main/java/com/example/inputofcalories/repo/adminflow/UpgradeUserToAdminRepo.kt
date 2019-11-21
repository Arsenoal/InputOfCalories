package com.example.inputofcalories.repo.adminflow

interface UpgradeUserToAdminRepo {
    suspend fun upgrade(userId: String)
}