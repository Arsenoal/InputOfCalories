package com.example.inputofcalories.repo.registration.mapper

import com.example.inputofcalories.common.mapper.Mapper
import com.example.inputofcalories.entity.User
import com.example.inputofcalories.repo.registration.model.UserGson

val userToGsonMapper = object: Mapper<User, UserGson> {
    override fun map(s: User): UserGson {
        return UserGson(
            id = s.id.toString(),
            name = s.userParams.name,
            email = s.userParams.email,
            gender = s.userParams.gender)
    }
}