package com.example.inputofcalories.repo.user

import com.example.inputofcalories.entity.register.User
import io.reactivex.Single

interface GetUserRepo {
    fun get(): Single<User>
}