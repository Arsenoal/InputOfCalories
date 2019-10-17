package com.example.inputofcalories.domain.registration

import com.example.inputofcalories.entity.UserParams
import io.reactivex.Single

interface RegisterUserUseCase {
    fun register(userParams: UserParams): Single<Any>
}