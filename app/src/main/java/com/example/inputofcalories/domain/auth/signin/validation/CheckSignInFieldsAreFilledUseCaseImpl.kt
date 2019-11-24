package com.example.inputofcalories.domain.auth.signin.validation

import com.example.inputofcalories.entity.register.UserSignInParams

class CheckSignInFieldsAreFilledUseCaseImpl: CheckSignInFieldsAreFilledUseCase {
    override suspend fun check(userSignInParams: UserSignInParams): Boolean {
        return userSignInParams.run {
                email.isNotBlank() && password.isNotBlank()
            }
    }
}