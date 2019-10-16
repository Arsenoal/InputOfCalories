package com.example.inputofcalories.repo.registration

import com.example.inputofcalories.common.BaseResponse
import com.example.inputofcalories.entity.User
import io.reactivex.Single

interface RegisterUserRepo {
    fun register(user: User): Single<Any>
}