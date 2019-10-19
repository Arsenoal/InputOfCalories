package com.example.inputofcalories.domain.registration

import com.example.inputofcalories.entity.register.UserRegistrationParams
import io.reactivex.Completable

interface RegisterUserUseCase {
    fun register(userRegistrationParams: UserRegistrationParams): Completable
}