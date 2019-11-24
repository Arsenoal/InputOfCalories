package com.example.inputofcalories.domain.auth.validation

private const val VALID_EMAIL_REGEX = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";

class CheckEmailFormatValidUseCaseImpl: CheckEmailFormatValidUseCase {
    override suspend fun check(email: String): Boolean {
        return email.matches(Regex(VALID_EMAIL_REGEX))
    }
}