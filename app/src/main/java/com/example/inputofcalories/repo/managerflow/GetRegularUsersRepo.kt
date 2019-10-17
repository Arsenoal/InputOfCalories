package com.example.inputofcalories.repo.managerflow

import com.example.inputofcalories.entity.User
import io.reactivex.Single

interface GetRegularUsersRepo {
    fun get(): Single<List<User>>
}