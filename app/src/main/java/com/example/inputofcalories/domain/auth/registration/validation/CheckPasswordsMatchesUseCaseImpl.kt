package com.example.inputofcalories.domain.auth.registration.validation

import io.reactivex.Single

class CheckPasswordsMatchesUseCaseImpl:
    CheckPasswordsMatchesUseCase {
    override fun check(p1: String, p2: String): Single<Boolean> {
        return Single.fromCallable {
            p1 == p2
        }
    }
}