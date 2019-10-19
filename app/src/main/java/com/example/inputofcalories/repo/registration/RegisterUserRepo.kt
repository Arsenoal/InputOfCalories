package com.example.inputofcalories.repo.registration

import com.example.inputofcalories.entity.register.UserRegistrationParams
import io.reactivex.Completable

interface RegisterUserRepo {
    fun register(userRegistrationParams: UserRegistrationParams): Completable
}