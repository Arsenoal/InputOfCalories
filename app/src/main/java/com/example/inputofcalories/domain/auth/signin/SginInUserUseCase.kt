package com.example.inputofcalories.domain.auth.signin

import com.example.inputofcalories.entity.register.User
import com.example.inputofcalories.entity.register.UserSignInParams
import io.reactivex.Single

interface SignInUserUseCase {
    fun signIn(userSignInParams: UserSignInParams): Single<User>
}