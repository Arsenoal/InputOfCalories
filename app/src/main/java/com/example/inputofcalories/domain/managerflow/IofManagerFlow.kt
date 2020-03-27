package com.example.inputofcalories.domain.managerflow

import com.example.inputofcalories.repo.managerflow.ManagerRepo

class IofManagerFlow(
    private val managerRepo: ManagerRepo
) : MangerFlowUseCase {

    override suspend fun downgradeToRegular(userId: String) = managerRepo.downgradeToRegular(userId)

    override suspend fun getUsers(userId: String) = managerRepo.getUsers(userId)

    override suspend fun upgradeToManager(userId: String) = managerRepo.upgradeToManager(userId)
}