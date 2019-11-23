package com.example.inputofcalories.domain.adminflow

import com.example.inputofcalories.repo.adminflow.AdminUserStatusManipulatorRepo

class AdminUserStatusManipulatorUseCaseImpl(
    private val adminUserStatusManipulatorRepo: AdminUserStatusManipulatorRepo
): AdminUserStatusManipulatorUseCase {
    override suspend fun downgradeToManager(userId: String) {
        adminUserStatusManipulatorRepo.downgradeToManager(userId)
    }

    override suspend fun downgradeToRegular(userId: String) {
        adminUserStatusManipulatorRepo.downgradeToRegular(userId)
    }

    override suspend fun upgradeToManager(userId: String) {
        adminUserStatusManipulatorRepo.upgradeToManager(userId)
    }

    override suspend fun upgradeToAdmin(userId: String) {
        adminUserStatusManipulatorRepo.upgradeToAdmin(userId)
    }
}