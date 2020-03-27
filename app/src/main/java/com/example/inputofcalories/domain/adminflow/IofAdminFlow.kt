package com.example.inputofcalories.domain.adminflow

import com.example.inputofcalories.repo.adminflow.AdminRepo

class IofAdminFlow(
    private val adminRepo: AdminRepo
): AdminFlowUseCase {
    override suspend fun downgradeToManager(userId: String) = adminRepo.downgradeToManager(userId)

    override suspend fun downgradeToRegular(userId: String) = adminRepo.downgradeToRegular(userId)

    override suspend fun upgradeToManager(userId: String) = adminRepo.upgradeToManager(userId)

    override suspend fun upgradeToAdmin(userId: String) = adminRepo.upgradeToAdmin(userId)

    override suspend fun getUsers(userId: String) = adminRepo.getUsers(userId)
}