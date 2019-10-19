package com.example.inputofcalories.domain.adminflow

import com.example.inputofcalories.entity.register.User
import io.reactivex.Single

interface GetAllUsersUseCase {
    fun get(): Single<List<User>>
}