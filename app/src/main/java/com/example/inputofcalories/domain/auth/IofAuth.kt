package com.example.inputofcalories.domain.auth

import com.example.inputofcalories.common.exception.RegistrationException
import com.example.inputofcalories.common.exception.UserException
import com.example.inputofcalories.entity.register.User
import com.example.inputofcalories.entity.register.UserRegistrationParams
import com.example.inputofcalories.entity.register.UserSignInParams
import com.example.inputofcalories.repo.auth.AuthRepo
import com.example.inputofcalories.repo.auth.GetAllUsersRepo

class IofAuth(
    private val authRepo: AuthRepo,
    private val allUsersRepo: GetAllUsersRepo
) : AuthUseCase {
    override suspend fun register(userRegistrationParams: UserRegistrationParams) {
        val isPresent = isUserWithEmailPresent(userRegistrationParams.email)

        if(!isPresent) {
            authRepo.register(userRegistrationParams)
        } else {
            throw RegistrationException(message = "user with params already present in firestore db")
        }
    }

    override suspend fun signIn(userSignInParams: UserSignInParams): User {
        getUserByEmail(userSignInParams.email)?.let { return it }

        throw UserException(message = "user with email: ${userSignInParams.email} not found")
    }

    private suspend fun getUserByEmail(email: String) = authRepo.signIn(email)

    private suspend fun isUserWithEmailPresent(email: String): Boolean {
        val meals = allUsersRepo.get()
        val emailMatchUsers = meals.filter { user ->
            user.userParams.email == email
        }

        return emailMatchUsers.isNotEmpty()
    }
}