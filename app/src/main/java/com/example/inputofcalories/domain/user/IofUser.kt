package com.example.inputofcalories.domain.user

import com.example.inputofcalories.entity.register.User
import com.example.inputofcalories.repo.user.UserRepo

class IofUser(
    private val userRepo: UserRepo
): UserUseCase {
    override suspend fun get() = userRepo.get()

    override suspend fun set(user: User) = userRepo.set(user)

}