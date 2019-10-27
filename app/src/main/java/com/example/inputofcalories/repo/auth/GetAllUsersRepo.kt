package com.example.inputofcalories.repo.auth

import com.example.inputofcalories.entity.register.User
import io.reactivex.Single

interface GetAllUsersRepo {
    fun get(): Single<List<User>>
}