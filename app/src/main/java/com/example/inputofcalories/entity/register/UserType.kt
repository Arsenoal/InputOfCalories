package com.example.inputofcalories.entity.register

sealed class UserType

object RegularUser: UserType()

object UserManager: UserType()

object Admin: UserType()