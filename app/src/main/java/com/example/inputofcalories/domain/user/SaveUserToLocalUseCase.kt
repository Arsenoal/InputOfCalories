package com.example.inputofcalories.domain.user

import com.example.inputofcalories.entity.register.User
import io.reactivex.Completable

interface SaveUserToLocalUseCase {
    fun save(user: User): Completable
}