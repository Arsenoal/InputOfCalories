package com.example.inputofcalories.domain.managerflow

import com.example.inputofcalories.repo.managerflow.GetUsersRepo

class GetUsersUseCaseImpl(
    private val getUsersRepo: GetUsersRepo
): GetUsersUseCase {

    override fun get(userId: String) = getUsersRepo.get(userId)
}