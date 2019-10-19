package com.example.inputofcalories.entity.register

data class User(
    val id: String? = null,
    val userParams: UserParams
)

data class UserParams(
    val name: String,
    val email: String
)

data class UserRegistrationParams(
    val email: String,
    val name: String,
    val password: String,
    val repeatPassword: String
)

data class UserUpdateParams(
    val name: String
)