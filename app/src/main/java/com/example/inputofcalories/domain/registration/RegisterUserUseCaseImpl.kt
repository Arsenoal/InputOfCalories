package com.example.inputofcalories.domain.registration

import com.example.inputofcalories.entity.User
import com.example.inputofcalories.entity.UserParams
import com.example.inputofcalories.repo.registration.RegisterUserRepo
import com.example.inputofcalories.repo.registration.idcreator.IdCreatorRepo
import io.reactivex.Single

class RegisterUserUseCaseImpl(
    private val registerUserRepo: RegisterUserRepo,
    private val idCreatorRepo: IdCreatorRepo
): RegisterUserUseCase {
    override fun register(userParams: UserParams): Single<Any> {
        return idCreatorRepo.get().flatMap {
            val user = User(it, userParams)

            registerUserRepo.register(user)
        }
    }
}