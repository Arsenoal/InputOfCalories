package com.example.inputofcalories.domain.auth.registration

import com.example.inputofcalories.common.exception.RegistrationException
import com.example.inputofcalories.entity.register.UserRegistrationParams
import com.example.inputofcalories.repo.auth.GetAllUsersRepo
import com.example.inputofcalories.repo.auth.registration.RegisterUserRepo
import io.reactivex.Completable
import io.reactivex.Single

class RegisterUserUseCaseImpl(
    private val registerUserRepo: RegisterUserRepo,
    private val allUsersRepo: GetAllUsersRepo
): RegisterUserUseCase {

    override suspend fun register(userRegistrationParams: UserRegistrationParams) {

        val isPresent = isUserWithEmailPresent(userRegistrationParams.email)

        if(!isPresent) {
            registerUserRepo.register(userRegistrationParams)
        } else {
            throw RegistrationException(message = "user with params already present in firestore db")
        }
    }

    private suspend fun isUserWithEmailPresent(email: String): Boolean {
        val meals = allUsersRepo.get()
        val emailMatchUsers = meals.filter { user ->
            user.userParams.email == email
        }

        return emailMatchUsers.isNotEmpty()
    }

}