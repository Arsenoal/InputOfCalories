package com.example.inputofcalories.domain.managerflow

import com.example.inputofcalories.repo.adminflow.DowngradeUserToRegularRepo

class DowngradeUserToRegularUseCaseImpl(
    private val downgradeUserToMangerRepo: DowngradeUserToRegularRepo
): DowngradeUserToRegularUseCase {

    override fun downgrade(userId: String) = downgradeUserToMangerRepo.downgrade(userId)
}