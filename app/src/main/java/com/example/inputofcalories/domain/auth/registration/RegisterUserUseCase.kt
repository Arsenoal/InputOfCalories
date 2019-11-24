package com.example.inputofcalories.domain.auth.registration

import com.example.inputofcalories.entity.register.UserRegistrationParams

interface RegisterUserUseCase {
    suspend fun register(userRegistrationParams: UserRegistrationParams)
}