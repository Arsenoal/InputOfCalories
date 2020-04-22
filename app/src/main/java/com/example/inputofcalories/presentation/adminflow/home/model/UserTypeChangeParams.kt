package com.example.inputofcalories.presentation.adminflow.home.model

import com.example.inputofcalories.entity.register.UserType

data class UserTypeChangeParams(
    val userId: String,
    val type: UserType,
    val adapterPosition: Int
)