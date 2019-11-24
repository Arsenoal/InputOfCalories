package com.example.inputofcalories.domain.managerflow

import com.example.inputofcalories.repo.managerflow.DowngradeManagerToRegularUserRepo

class DowngradeUserToRegularUseCaseImpl(
    private val downgradeUserToMangerRepo: DowngradeManagerToRegularUserRepo
): DowngradeUserToRegularUseCase {

    override suspend fun downgrade(userId: String) = downgradeUserToMangerRepo.downgrade(userId)
}