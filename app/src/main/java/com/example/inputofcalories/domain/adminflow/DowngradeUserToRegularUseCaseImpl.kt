package com.example.inputofcalories.domain.adminflow

import com.example.inputofcalories.repo.adminflow.DowngradeUserToRegularRepo

class DowngradeUserToRegularUseCaseImpl(
    private val downgradeUserToRegularRepo: DowngradeUserToRegularRepo
): DowngradeUserToRegularUseCase {
    override suspend fun downgrade(userId: String) = downgradeUserToRegularRepo.downgrade(userId)
}