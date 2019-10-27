package com.example.inputofcalories.domain.auth.registration.validation

import io.reactivex.Single

private const val VALID_EMAIL_REGEX = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";

class CheckEmailFormatValidUseCaseImpl:
    CheckEmailFormatValidUseCase {
    override fun check(email: String): Single<Boolean> {
        return Single.fromCallable {
            email.matches(Regex(VALID_EMAIL_REGEX))
        }
    }
}