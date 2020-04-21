package com.example.inputofcalories.domain.adminflow

import com.example.inputofcalories.repo.adminflow.AdminRepo
import com.example.inputofcalories.repo.user.UserRepo

class IofAdminFlow(
    private val adminRepo: AdminRepo,
    private val userRepo: UserRepo
): AdminFlowUseCase {
    override suspend fun downgradeToManager(userId: String) = with(adminRepo) { downgradeToManager(userId) }

    override suspend fun downgradeToRegular(userId: String) = with(adminRepo) { downgradeToRegular(userId) }

    override suspend fun upgradeToManager(userId: String) = with(adminRepo) { upgradeToManager(userId) }

    override suspend fun upgradeToAdmin(userId: String) = with(adminRepo) { upgradeToAdmin(userId) }

    override suspend fun getUsers() = with(adminRepo) { getUsers(userRepo.get().id) }
}