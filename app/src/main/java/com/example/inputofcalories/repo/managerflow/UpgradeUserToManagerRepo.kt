package com.example.inputofcalories.repo.managerflow

interface UpgradeUserToManagerRepo {
    suspend fun upgrade(userId: String)
}