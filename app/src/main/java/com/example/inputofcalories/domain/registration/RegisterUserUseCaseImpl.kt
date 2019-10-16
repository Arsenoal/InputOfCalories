package com.example.inputofcalories.domain.registration

import com.example.inputofcalories.entity.User
import com.example.inputofcalories.repo.registration.RegisterUserRepo

class RegisterUserUseCaseImpl(
    private val registerUserRepo: RegisterUserRepo
): RegisterUserUseCase {
    override fun register(user: User) = registerUserRepo.register(user)
}