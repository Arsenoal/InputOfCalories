package com.example.inputofcalories.repo.auth.model

import com.example.inputofcalories.common.extensions.empty

const val TYPE_REGULAR = 1
const val TYPE_MANAGER = 2
const val TYPE_ADMIN = 3

sealed class UserFirebaseType {
    class REGULAR(val type: Int = TYPE_REGULAR): UserFirebaseType()

    class MANAGER(val type: Int = TYPE_MANAGER): UserFirebaseType()

    class ADMIN(val type: Int = TYPE_ADMIN): UserFirebaseType()
}

class UserFirebase(
    val id: String,
    val name: String,
    val email: String,
    val dailyCalories: String,
    val password: String,
    val type: Int) {

    constructor(): this(String.empty(), String.empty(), String.empty(), String.empty(), String.empty(), -1)
}