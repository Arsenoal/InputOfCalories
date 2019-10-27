package com.example.inputofcalories.domain.auth.registration

import com.example.inputofcalories.entity.register.UserRegistrationParams
import com.example.inputofcalories.repo.auth.GetAllUsersRepo
import com.example.inputofcalories.repo.auth.registration.RegisterUserRepo
import io.reactivex.Completable
import io.reactivex.Single

class RegisterUserUseCaseImpl(
    private val registerUserRepo: RegisterUserRepo,
    private val allUsersRepo: GetAllUsersRepo
): RegisterUserUseCase {

    override fun register(userRegistrationParams: UserRegistrationParams): Completable {
        return isUserWithEmailPresent(userRegistrationParams.email).flatMapCompletable { registerUserRepo.register(userRegistrationParams) }
    }

    private fun isUserWithEmailPresent(email: String): Single<Boolean> {
        return allUsersRepo.get().map { meals ->
            val emailMatchUsers = meals.filter { user ->
                user.userParams.email == email
            }

            emailMatchUsers.isNotEmpty()
        }
    }

}