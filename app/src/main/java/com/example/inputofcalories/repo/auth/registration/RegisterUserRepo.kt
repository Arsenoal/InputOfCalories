package com.example.inputofcalories.repo.auth.registration

import com.example.inputofcalories.entity.register.UserRegistrationParams

interface RegisterUserRepo {
    suspend fun register(userRegistrationParams: UserRegistrationParams)
}