package com.example.inputofcalories.domain.managerflow

import com.example.inputofcalories.entity.register.User
import io.reactivex.Single

interface GetUsersUseCase {
    fun get(userId: String): Single<List<User>>
}