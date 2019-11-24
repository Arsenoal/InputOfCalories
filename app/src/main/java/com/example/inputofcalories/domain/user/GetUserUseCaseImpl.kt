package com.example.inputofcalories.domain.user

import com.example.inputofcalories.repo.user.GetUserRepo

class GetUserUseCaseImpl(
    private val getUserRepo: GetUserRepo
): GetUserUseCase {
    override suspend fun get() = getUserRepo.get()
}