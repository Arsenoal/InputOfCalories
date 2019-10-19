package com.example.inputofcalories.repo.adminflow

import com.example.inputofcalories.entity.register.User
import io.reactivex.Single

interface GetAllUsersRepo {
    fun get(): Single<List<User>>
}