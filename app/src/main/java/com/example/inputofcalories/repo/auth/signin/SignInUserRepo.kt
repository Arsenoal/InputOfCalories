package com.example.inputofcalories.repo.auth.signin

import com.example.inputofcalories.entity.register.User
import com.example.inputofcalories.entity.register.UserSignInParams
import io.reactivex.Single

interface SignInUserRepo {
    fun signIn(userSignInParams: UserSignInParams): Single<User>
}