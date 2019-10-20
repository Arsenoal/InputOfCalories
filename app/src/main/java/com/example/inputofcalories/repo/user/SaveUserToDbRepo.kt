package com.example.inputofcalories.repo.user

import com.example.inputofcalories.entity.register.User
import io.reactivex.Completable

interface SaveUserToDbRepo {
    fun save(user: User): Completable
}