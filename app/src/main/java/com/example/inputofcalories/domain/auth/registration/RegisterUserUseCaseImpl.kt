package com.example.inputofcalories.domain.auth.registration

import com.example.inputofcalories.entity.register.UserRegistrationParams
import com.example.inputofcalories.repo.auth.registration.RegisterUserRepo

class RegisterUserUseCaseImpl(
    private val registerUserRepo: RegisterUserRepo
): RegisterUserUseCase {

    override fun register(userRegistrationParams: UserRegistrationParams) = registerUserRepo.register(userRegistrationParams)

}