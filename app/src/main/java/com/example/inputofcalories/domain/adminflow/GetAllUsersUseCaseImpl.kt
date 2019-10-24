package com.example.inputofcalories.domain.adminflow

import com.example.inputofcalories.repo.adminflow.GetAllUsersRepo

class GetAllUsersUseCaseImpl(
    private val getAllUsersRepo: GetAllUsersRepo
): GetAllUsersUseCase {
    override fun get(userId: String) = getAllUsersRepo.get(userId)
}