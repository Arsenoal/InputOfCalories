package com.example.inputofcalories.domain.auth.registration

import com.example.inputofcalories.entity.register.UserRegistrationParams
import io.reactivex.Completable

interface RegisterUserUseCase {
    fun register(userRegistrationParams: UserRegistrationParams): Completable
}