package com.example.inputofcalories.domain.managerflow

import com.example.inputofcalories.entity.register.User
import io.reactivex.Single

interface GetRegularUsersUseCase {
    fun get(): Single<List<User>>
}