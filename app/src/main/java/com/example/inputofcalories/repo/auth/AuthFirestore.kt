package com.example.inputofcalories.repo.auth

import com.example.inputofcalories.common.exception.RegistrationException
import com.example.inputofcalories.common.exception.SignInException
import com.example.inputofcalories.entity.register.*
import com.example.inputofcalories.repo.auth.model.TYPE_ADMIN
import com.example.inputofcalories.repo.auth.model.TYPE_MANAGER
import com.example.inputofcalories.repo.auth.model.TYPE_REGULAR
import com.example.inputofcalories.repo.auth.model.UserFirebase
import com.example.inputofcalories.repo.db.FirebaseDataBaseCollectionNames
import com.example.inputofcalories.repo.service.EncoderService

import kotlin.coroutines.resumeWithException
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.ExperimentalCoroutinesApi

import com.google.firebase.firestore.FirebaseFirestore

class AuthFirestore(
    private val firestore: FirebaseFirestore,
    private val encoderService: EncoderService
) : AuthRepo {

    @ExperimentalCoroutinesApi
    override suspend fun register(userRegistrationParams: UserRegistrationParams, result: (Any) -> Unit) {
        val usersRef = firestore.collection(FirebaseDataBaseCollectionNames.USERS)

        val uId = encoderService.encode(userRegistrationParams.email)

        result.invoke(
            suspendCancellableCoroutine { continuation ->
                usersRef
                    .document(uId)
                    .get()
                    .addOnSuccessListener {
                        if(it.exists()) continuation.resumeWithException(RegistrationException(message = "user with params already present in firestore db"))

                        val userFirebase = with(userRegistrationParams) {
                            UserFirebase(id = uId, name = name, email = email, password = password, dailyCalories = dailyCalories, type = TYPE_REGULAR)
                        }

                        usersRef
                            .document(uId)
                            .set(userFirebase)
                            .addOnSuccessListener { continuation.resume(Any()){ throw RegistrationException() } }
                            .addOnFailureListener { error -> continuation.resumeWithException(RegistrationException(error = error)) }
                    }
                    .addOnFailureListener {
                        continuation.resumeWithException(RegistrationException())
                    }
            }
        )
    }

    @ExperimentalCoroutinesApi
    override suspend fun signIn(email: String): User? {
        val uId = encoderService.encode(email)

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

                        User(id = it.id, userParams = UserParams(name = name, email = email, dailyCalories = dailyCalories, type = type))
                    }

                    continuation.resume(user) { throw SignInException() }
                }
                .addOnFailureListener { continuation.resumeWithException(SignInException(message = "user with email: $email not found")) }
        }
    }
}