package com.example.inputofcalories.domain.auth.signin.validation

import com.example.inputofcalories.entity.register.UserSignInParams
import io.reactivex.Single

class CheckSignInFieldsAreFilledUseCaseImpl: CheckSignInFieldsAreFilledUseCase {
    override fun check(userSignInParams: UserSignInParams): Single<Boolean> {
        return Single.fromCallable {
            userSignInParams.run {
                email.isNotBlank() && password.isNotBlank()
            }
        }
    }
}