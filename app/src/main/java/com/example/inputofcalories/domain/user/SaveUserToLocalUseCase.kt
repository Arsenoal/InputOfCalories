package com.example.inputofcalories.domain.user

import com.example.inputofcalories.entity.register.User

interface SaveUserToLocalUseCase {
    suspend fun save(user: User)
}