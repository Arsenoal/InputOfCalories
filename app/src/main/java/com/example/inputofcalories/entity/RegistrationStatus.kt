package com.example.inputofcalories.entity

sealed class RegistrationStatus

object NotRegistered: RegistrationStatus()

object RegularUser: RegistrationStatus()

object UserManager: RegistrationStatus()

object Admin: RegistrationStatus()