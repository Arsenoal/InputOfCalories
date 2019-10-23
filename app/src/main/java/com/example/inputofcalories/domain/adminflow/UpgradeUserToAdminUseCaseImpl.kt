package com.example.inputofcalories.domain.adminflow

import com.example.inputofcalories.repo.adminflow.UpgradeUserToAdminRepo

class UpgradeUserToAdminUseCaseImpl(
    private val upgradeUserToAdminRepo: UpgradeUserToAdminRepo
): UpgradeUserToAdminUseCase {
    override fun upgrade(userId: String) = upgradeUserToAdminRepo.upgrade(userId)
}