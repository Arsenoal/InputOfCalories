package com.example.inputofcalories.domain.user

import com.example.inputofcalories.entity.register.User
import com.example.inputofcalories.repo.user.SaveUserToDbRepo

class SaveUserToLocalUseCaseImpl(
    private val saveUserToDbRepo: SaveUserToDbRepo
): SaveUserToLocalUseCase {
    override suspend fun save(user: User) = saveUserToDbRepo.save(user)
}