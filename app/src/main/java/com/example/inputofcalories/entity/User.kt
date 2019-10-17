package com.example.inputofcalories.entity

import java.util.*

data class User(
    val id: UUID,
    val userParams: UserParams
)

data class UserParams(
    val name: String,
    val email: String,
    val gender: String
)