package com.example.inputofcalories.repo.user

import com.example.inputofcalories.entity.register.User

class UserInMemoryCacheRepo : UserRepo {

    lateinit var user: User

    override suspend fun get() = user

    override suspend fun set(user: User) {
        this.user = user
    }
}