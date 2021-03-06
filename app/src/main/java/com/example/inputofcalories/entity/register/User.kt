package com.example.inputofcalories.entity.register

import com.example.inputofcalories.common.extensions.empty

data class User(
    val id: String = String.empty(),
    val userParams: UserParams
)

data class UserParams(
    val name: String,
    val email: String,
    val dailyCalories: String,
    var type: UserType
)

data class UserRegistrationParams(
    val email: String,
    val name: String,
    val dailyCalories: String = String.empty(),
    val password: String,
    val repeatPassword: String
)

data class UserSignInParams(
    val email: String,
    val password: String
)