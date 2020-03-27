package com.example.inputofcalories.domain.auth

import com.example.inputofcalories.domain.auth.validation.AuthValidationUseCase
import com.example.inputofcalories.entity.register.UserRegistrationParams
import com.example.inputofcalories.entity.register.UserSignInParams

private const val VALID_EMAIL_REGEX = "^[\\w-_.+]*[\\w-_.]@([\\w]+\\.)+[\\w]+[\\w]$";

class IofAuthValidation: AuthValidationUseCase {

    override suspend fun checkPasswordsMatch(p1: String, p2: String) = p1 == p2

    override suspend fun checkRegistrationFieldsAreFilled(userRegistrationParams: UserRegistrationParams) = with(userRegistrationParams) {
        name.isNotBlank() && email.isNotBlank() && password.isNotBlank() && repeatPassword.isNotBlank()
    }

    override suspend fun checkSignInFieldsAreFilled(userSignInParams: UserSignInParams) = with(userSignInParams) {
        email.isNotBlank() && password.isNotBlank()
    }

    override suspend fun checkEmailFormat(email: String) = email.matches(Regex(VALID_EMAIL_REGEX))
}