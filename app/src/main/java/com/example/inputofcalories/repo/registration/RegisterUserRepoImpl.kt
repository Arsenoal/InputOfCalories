package com.example.inputofcalories.repo.registration

import com.example.inputofcalories.common.mapper.ExceptionMapper
import com.example.inputofcalories.entity.User
import com.example.inputofcalories.repo.registration.mapper.userToGsonMapper
import io.reactivex.Single

class RegisterUserRepoImpl(
    private val registrationService: RegistrationService
): RegisterUserRepo {
    override fun register(user: User): Single<Any> {
        val exceptionMapper = ExceptionMapper<Any>()

        return registrationService.register(userToGsonMapper.map(user)).map { exceptionMapper.map(it) }
    }
}