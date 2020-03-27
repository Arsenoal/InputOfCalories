package com.example.inputofcalories.domain.auth.validation

import com.example.inputofcalories.entity.register.UserRegistrationParams
import com.example.inputofcalories.entity.register.UserSignInParams

interface AuthValidationUseCase {
    suspend fun checkPasswordsMatch(p1: String, p2: String): Boolean

    suspend fun checkRegistrationFieldsAreFilled(userRegistrationParams: UserRegistrationParams): Boolean

    suspend fun checkSignInFieldsAreFilled(userSignInParams: UserSignInParams): Boolean

    suspend fun checkEmailFormat(email: String): Boolean
}