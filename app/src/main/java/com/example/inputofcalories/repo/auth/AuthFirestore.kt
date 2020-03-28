package com.example.inputofcalories.repo.auth

import com.example.inputofcalories.common.exception.RegistrationException
import com.example.inputofcalories.common.exception.SignInException
import com.example.inputofcalories.common.exception.UserException
import com.example.inputofcalories.entity.register.*
import com.example.inputofcalories.repo.auth.model.TYPE_ADMIN
import com.example.inputofcalories.repo.auth.model.TYPE_MANAGER
import com.example.inputofcalories.repo.auth.model.TYPE_REGULAR
import com.example.inputofcalories.repo.auth.model.UserFirebase
import com.example.inputofcalories.repo.db.FirebaseDataBaseCollectionNames
import com.example.inputofcalories.repo.service.SHACreatorService

import kotlin.coroutines.resumeWithException
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.ExperimentalCoroutinesApi

import com.google.firebase.firestore.FirebaseFirestore

class AuthFirestore(
    private val firestore: FirebaseFirestore,
    private val shaCreatorService: SHACreatorService
) : AuthRepo {

    override suspend fun register(userRegistrationParams: UserRegistrationParams) {
        val usersRef = firestore.collection(FirebaseDataBaseCollectionNames.USERS)

        val uId = shaCreatorService.encrypt(userRegistrationParams.email)

        usersRef
            .document(uId)
            .get()
            .addOnFailureListener {
                val userFirebase = UserFirebase(
                    id = uId,
                    name = userRegistrationParams.name,
                    email = userRegistrationParams.email,
                    password = userRegistrationParams.password,
                    dailyCalories = userRegistrationParams.dailyCalories,
                    type = TYPE_REGULAR
                )

                usersRef
                    .document(uId)
                    .set(userFirebase)
                    .addOnFailureListener { throw RegistrationException(error = it) }
            }
    }

    @ExperimentalCoroutinesApi
    override suspend fun signIn(email: String): User? {
        val uId = shaCreatorService.encrypt(email)

        return suspendCancellableCoroutine { continuation ->

            val usersRef = firestore.collection(FirebaseDataBaseCollectionNames.USERS)

            usersRef
                .document(uId)
                .get()
                .addOnSuccessListener {
                    val user = it.toObject(UserFirebase::class.java)?.run {
                        val type: UserType = when (type) {
                            TYPE_MANAGER -> { UserManager }
                            TYPE_ADMIN -> { Admin }
                            else -> { RegularUser }
                        }

                        User(
                            id = it.id,
                            userParams = UserParams(
                                name = name,
                                email = email,
                                dailyCalories = dailyCalories,
                                type = type)
                        )
                    }

                    continuation.resume(user) { throw SignInException() }
                }
                .addOnFailureListener { error ->
                    continuation.resumeWithException(SignInException(message = "user with email: $email not found"))
                }
        }
    }
}