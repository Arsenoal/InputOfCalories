package com.example.inputofcalories.domain.auth

import com.example.inputofcalories.entity.register.UserRegistrationParams
import com.example.inputofcalories.entity.register.UserSignInParams
import com.example.inputofcalories.repo.auth.AuthRepo

class IofAuth(
    private val authRepo: AuthRepo
) : AuthUseCase {

    override suspend fun register(userRegistrationParams: UserRegistrationParams, result: (Any) -> Unit) = authRepo.register(userRegistrationParams, result)

    override suspend fun signIn(userSignInParams: UserSignInParams) = with(userSignInParams) {
        authRepo.signIn(email)
    }
}