package com.example.inputofcalories.domain.adminflow

import com.example.inputofcalories.repo.adminflow.UpgradeUserToManagerRepo

class UpgradeUserToManagerUseCaseImpl(
    private val upgradeUserToManagerRepo: UpgradeUserToManagerRepo
): UpgradeUserToManagerUseCase {
    override fun upgrade(userId: String) = upgradeUserToManagerRepo.upgrade(userId)
}