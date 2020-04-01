package com.example.inputofcalories.domain.auth

import com.example.inputofcalories.entity.register.UserRegistrationParams
import com.example.inputofcalories.entity.register.UserSignInParams
import com.example.inputofcalories.repo.auth.AuthRepo

class IofAuth(private val authRepo: AuthRepo) : AuthUseCase {

    override suspend fun register(userRegistrationParams: UserRegistrationParams) = authRepo.register(userRegistrationParams)

    override suspend fun signIn(userSignInParams: UserSignInParams) = authRepo.signIn(userSignInParams)
}