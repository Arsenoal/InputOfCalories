package com.example.inputofcalories.domain.adminflow

import com.example.inputofcalories.repo.adminflow.DowngradeUserToManagerRepo

class DowngradeUserToManagerUseCaseImpl(
    private val downgradeUserToManagerRepo: DowngradeUserToManagerRepo
): DowngradeUserToManagerUseCase {
    override suspend fun downgrade(userId: String) = downgradeUserToManagerRepo.downgrade(userId)
}