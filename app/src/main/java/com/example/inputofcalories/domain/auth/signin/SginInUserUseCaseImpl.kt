package com.example.inputofcalories.domain.auth.signin

import com.example.inputofcalories.entity.register.UserSignInParams
import com.example.inputofcalories.repo.auth.signin.SignInUserRepo

class SignInUserUseCaseImpl(
    private val signInUserRepo: SignInUserRepo
): SignInUserUseCase {
    override fun signIn(userSignInParams: UserSignInParams) = signInUserRepo.signIn(userSignInParams)
}