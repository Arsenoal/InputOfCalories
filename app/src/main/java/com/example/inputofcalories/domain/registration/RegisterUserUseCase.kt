package com.example.inputofcalories.domain.registration

import com.example.inputofcalories.entity.User
import io.reactivex.Single

interface RegisterUserUseCase {
    fun register(user: User): Single<Any>
}