package com.example.inputofcalories.repo.adminflow

interface UpgradeUserToManagerRepo {
    suspend fun upgrade(userId: String)
}