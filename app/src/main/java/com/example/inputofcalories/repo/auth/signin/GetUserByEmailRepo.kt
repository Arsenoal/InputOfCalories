package com.example.inputofcalories.repo.auth.signin

import com.example.inputofcalories.entity.register.User

interface GetUserByEmailRepo {
    suspend fun get(email: String): User?
}