package com.example.inputofcalories.domain.managerflow

import com.example.inputofcalories.repo.managerflow.UpgradeUserToManagerRepo

class UpgradeUserToManagerUseCaseImpl(
    private val upgradeUserToManagerRepo: UpgradeUserToManagerRepo
): UpgradeUserToManagerUseCase {

    override fun upgrade(userId: String) = upgradeUserToManagerRepo.upgrade(userId)
}