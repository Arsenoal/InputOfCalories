package com.example.inputofcalories.domain.adminflow

import com.example.inputofcalories.repo.adminflow.AdminRepo
import com.example.inputofcalories.repo.user.UserRepo

class IofAdminFlow(
    private val adminRepo: AdminRepo,
    private val userRepo: UserRepo
): AdminFlowUseCase {

    override suspend fun changeUserType(userId: String, newType: Int) = with(adminRepo) { changeUserType(userId, newType) }

    override suspend fun getUsers() = with(adminRepo) { getUsers(userRepo.get().id) }
}