package com.example.inputofcalories.domain.adminflow

import com.example.inputofcalories.entity.register.User
import io.reactivex.Single

interface GetAllUsersUseCase {
    fun get(userId: String): Single<List<User>>
}