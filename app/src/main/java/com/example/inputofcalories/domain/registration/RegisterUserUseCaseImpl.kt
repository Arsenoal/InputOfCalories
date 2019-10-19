package com.example.inputofcalories.domain.registration

import com.example.inputofcalories.entity.register.UserRegistrationParams
import com.example.inputofcalories.repo.registration.RegisterUserRepo
import com.example.inputofcalories.repo.registration.UpdateUserRepo

class RegisterUserUseCaseImpl(
    private val registerUserRepo: RegisterUserRepo
): RegisterUserUseCase {

    override fun register(userRegistrationParams: UserRegistrationParams) = registerUserRepo.register(userRegistrationParams)

}