package com.example.inputofcalories.domain.auth.registration.validation

import com.example.inputofcalories.entity.register.UserRegistrationParams
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.suspendCancellableCoroutine

class CheckRegistrationFieldsAreFilledUseCaseImpl: CheckRegistrationFieldsAreFilledUseCase {
    @ExperimentalCoroutinesApi
    override suspend fun check(userRegistrationParams: UserRegistrationParams): Boolean {

        return suspendCancellableCoroutine { continuation ->
            userRegistrationParams.run {
                continuation.resume(name.isNotBlank() && email.isNotBlank() && password.isNotBlank() && repeatPassword.isNotBlank()) {}
            }
        }
    }
}