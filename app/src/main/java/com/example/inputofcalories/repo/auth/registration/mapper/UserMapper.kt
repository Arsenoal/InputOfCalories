package com.example.inputofcalories.repo.auth.registration.mapper

import com.example.inputofcalories.common.mapper.Mapper
import com.example.inputofcalories.entity.register.User
import com.example.inputofcalories.entity.register.UserParams
import com.google.firebase.auth.FirebaseUser

val firebaseUserToUserMapper = object: Mapper<FirebaseUser, User> {
    override fun map(s: FirebaseUser): User {
        val userParams = UserParams(
            name = s.displayName ?: "",
            email = s.email ?: ""
        )
        return User(s.uid, userParams)
    }
}