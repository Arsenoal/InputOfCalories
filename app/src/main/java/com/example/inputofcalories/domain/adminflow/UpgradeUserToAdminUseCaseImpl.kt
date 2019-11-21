package com.example.inputofcalories.domain.adminflow

import com.example.inputofcalories.repo.adminflow.UpgradeUserToAdminRepo

class UpgradeUserToAdminUseCaseImpl(
    private val upgradeUserToAdminRepo: UpgradeUserToAdminRepo
): UpgradeUserToAdminUseCase {
    override suspend fun upgrade(userId: String) = upgradeUserToAdminRepo.upgrade(userId)
}