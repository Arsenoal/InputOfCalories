package com.example.inputofcalories.domain.auth.signin.validation

import com.example.inputofcalories.entity.register.UserSignInParams
import io.reactivex.Single

interface CheckSignInFieldsAreFilledUseCase {
    fun check(userSignInParams: UserSignInParams): Single<Boolean>
}