package com.example.inputofcalories.repo.user

import com.example.inputofcalories.entity.register.User

interface SaveUserToDbRepo {
    suspend fun save(user: User)
}