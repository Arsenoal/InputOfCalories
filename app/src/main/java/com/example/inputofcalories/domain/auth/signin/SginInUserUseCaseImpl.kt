package com.example.inputofcalories.domain.auth.signin

import com.example.inputofcalories.common.exception.UserException
import com.example.inputofcalories.entity.register.User
import com.example.inputofcalories.entity.register.UserSignInParams
import com.example.inputofcalories.repo.auth.signin.GetUserByEmailRepo

class SignInUserUseCaseImpl(
    private val getUserByEmailRepo: GetUserByEmailRepo
): SignInUserUseCase {
    override suspend fun signIn(userSignInParams: UserSignInParams): User {

        getUserByEmail(userSignInParams.email)?.let {
            return it
        }

        throw UserException(message = "user with email: ${userSignInParams.email} not found")
    }

    private suspend fun getUserByEmail(email: String) = getUserByEmailRepo.get(email)
}