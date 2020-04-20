package com.example.inputofcalories.domain.managerflow

import com.example.inputofcalories.repo.managerflow.ManagerRepo
import com.example.inputofcalories.repo.user.UserRepo

class IofManagerFlow(
    private val managerRepo: ManagerRepo,
    private val userRepo: UserRepo
) : MangerFlowUseCase {

    override suspend fun downgradeToRegular(userId: String) = managerRepo.downgradeToRegular(userId)

    override suspend fun getUsers() = managerRepo.getUsers(userRepo.get().id)

    override suspend fun upgradeToManager(userId: String) = managerRepo.upgradeToManager(userId)
}