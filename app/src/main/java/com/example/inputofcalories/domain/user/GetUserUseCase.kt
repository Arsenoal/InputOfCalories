package com.example.inputofcalories.domain.user

import com.example.inputofcalories.entity.register.User
import io.reactivex.Single

interface GetUserUseCase {
    fun get(): Single<User>
}