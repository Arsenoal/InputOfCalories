package com.example.inputofcalories.repo.launch.mapper

import com.example.inputofcalories.common.mapper.Mapper
import com.example.inputofcalories.entity.*

val registrationStateMapper = object: Mapper<Int, RegistrationStatus> {
    override fun map(s: Int): RegistrationStatus {
        return when(s) {
            0 -> NotRegistered
            1 -> RegularUser
            2 -> UserManager
            else -> Admin
        }
    }
}