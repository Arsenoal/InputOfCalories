package com.example.inputofcalories.entity.register

sealed class UserStatus

object NotRegistered: UserStatus()

object RegularUser: UserStatus()

object UserManager: UserStatus()

object Admin: UserStatus()