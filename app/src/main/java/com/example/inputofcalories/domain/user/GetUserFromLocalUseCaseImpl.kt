package com.example.inputofcalories.domain.user

import com.example.inputofcalories.repo.user.GetUserFromLocalRepo

class GetUserFromLocalUseCaseImpl(
    private val getUserFromLocalRepo: GetUserFromLocalRepo
): GetUserFromLocalUseCase {
    override fun get() = getUserFromLocalRepo.get()
}