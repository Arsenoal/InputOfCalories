package com.example.inputofcalories.domain.auth.registration.validation

import com.example.inputofcalories.entity.register.UserRegistrationParams
import io.reactivex.Single

class CheckRegistrationFieldsAreFilledUseCaseImpl: CheckRegistrationFieldsAreFilledUseCase {
    override fun check(userRegistrationParams: UserRegistrationParams): Single<Boolean> {
        return Single.fromCallable {
            userRegistrationParams.run {
                name.isNotBlank() && email.isNotBlank() && password.isNotBlank() && repeatPassword.isNotBlank()
            }
        }
    }
}