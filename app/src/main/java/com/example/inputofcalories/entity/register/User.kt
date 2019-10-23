package com.example.inputofcalories.entity.register

import com.example.inputofcalories.common.extensions.empty

data class User(
    val id: String = String.empty(),
    val userParams: UserParams
)

data class UserParams(
    val name: String,
    val email: String,
    val type: UserType
)

data class UserRegistrationParams(
    val email: String,
    val name: String,
    val password: String,
    val repeatPassword: String
)

data class UserSignInParams(
    val email: String,
    val password: String
)