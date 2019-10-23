package com.example.inputofcalories.domain.managerflow

import com.example.inputofcalories.repo.managerflow.GetUsersRepo

class GetRegularUsersUseCaseImpl(
    private val getUsersRepo: GetUsersRepo
): GetRegularUsersUseCase {

    override fun get() = getUsersRepo.get()
}