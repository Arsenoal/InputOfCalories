package com.example.inputofcalories.repo.managerflow

import com.example.inputofcalories.entity.register.User
import io.reactivex.Single

interface GetUsersRepo {
    fun get(userId: String): Single<List<User>>
}