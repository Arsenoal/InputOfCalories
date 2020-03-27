package com.example.inputofcalories.repo.auth

import com.example.inputofcalories.common.exception.RegistrationException
import com.example.inputofcalories.common.exception.UserException
import com.example.inputofcalories.entity.register.*
import com.example.inputofcalories.repo.auth.model.TYPE_ADMIN
import com.example.inputofcalories.repo.auth.model.TYPE_MANAGER
import com.example.inputofcalories.repo.auth.model.TYPE_REGULAR
import com.example.inputofcalories.repo.auth.model.UserFirebase
import com.example.inputofcalories.repo.common.service.UUIDGeneratorService
import com.example.inputofcalories.repo.db.FirebaseDataBaseCollectionNames

import kotlin.coroutines.resumeWithException
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.ExperimentalCoroutinesApi

import com.google.firebase.firestore.FirebaseFirestore

class AuthFirestore(
    private val firestore: FirebaseFirestore,
    private val uuidGeneratorService: UUIDGeneratorService
) : AuthRepo {

    override suspend fun register(userRegistrationParams: UserRegistrationParams) {
        val usersRef = firestore.collection(FirebaseDataBaseCollectionNames.USERS)

        val uId = uuidGeneratorService.get().toString()

        val userFirebase = UserFirebase(
            id = uId,
            name = userRegistrationParams.name,
            email = userRegistrationParams.email,
            password = userRegistrationParams.password,
            dailyCalories = userRegistrationParams.dailyCalories,
            type = TYPE_REGULAR
        )

        usersRef.document(uId).set(userFirebase).addOnFailureListener { throw RegistrationException(error = it) }
    }

    @ExperimentalCoroutinesApi
    override suspend fun signIn(email: String): User? {
        return suspendCancellableCoroutine { continuation ->
            val usersRef = firestore.collection(FirebaseDataBaseCollectionNames.USERS)

            usersRef.get()
                .addOnSuccessListener { querySnapshot ->
                    querySnapshot
                        .documents
                        .filter { documentSnapshot ->
                            val userFirebase = documentSnapshot.toObject(UserFirebase::class.java)

                            userFirebase?.email == email
                        }.map {
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

                            if (continuation.isActive)
                                continuation.resume(user) { throw UserException(message = "user with email: $email not found") }
                        }

                    if (continuation.isActive)
                        continuation.resumeWithException(UserException(message = "user with email: $email not found"))
                }
                .addOnFailureListener { error ->
                    continuation.resumeWithException(
                        UserException(message = error.message)
                    )
                }
        }
    }
}