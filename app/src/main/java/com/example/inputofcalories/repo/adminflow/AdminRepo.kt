package com.example.inputofcalories.repo.adminflow

import com.example.inputofcalories.entity.register.User

interface AdminRepo {
    suspend fun changeUserType(userId: String, newType: Int)

    suspend fun getUsers(userId: String): List<User>
}