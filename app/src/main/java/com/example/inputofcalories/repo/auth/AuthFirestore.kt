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
    override suspend fun register(userRegistrationParams: UserRegistrationParams) {

        val uId = encoderService.encode(userRegistrationParams.email)

        return suspendCancellableCoroutine { continuation ->

            val usersRef = firestore.collection(FirebaseDataBaseCollectionNames.USERS)

            usersRef
                .document(uId)
                .get()
                .addOnSuccessListener {
                    if (it.exists()) {
                        continuation.resumeWithException(RegistrationException(message = "user with params already present in firestore db"))
                    } else {

                        val userFirebase = with(userRegistrationParams) {
                            UserFirebase(id = uId, name = name, email = email, password = password, dailyCalories = dailyCalories, type = TYPE_REGULAR)
                        }

                        usersRef
                            .document(uId)
                            .set(userFirebase)
                            .addOnSuccessListener { continuation.resume(Unit) { throw RegistrationException() } }
                            .addOnFailureListener { error ->
                                continuation.resumeWithException(RegistrationException(error = error))
                            }
                    }
                }
                .addOnFailureListener { continuation.resumeWithException(RegistrationException()) }
            }
    }

    @ExperimentalCoroutinesApi
    override suspend fun signIn(userSignInParams: UserSignInParams): User? {

        val uId = encoderService.encode(userSignInParams.email)

        return suspendCancellableCoroutine<User> { continuation ->

            val usersRef = firestore.collection(FirebaseDataBaseCollectionNames.USERS)

            usersRef
                .document(uId)
                .get()
                .addOnSuccessListener {
                    val userFirebase = it.toObject(UserFirebase::class.java)
                    if (userFirebase != null) {
                        userFirebase.run {
                            val type: UserType = when (type) {
                                TYPE_MANAGER -> { UserManager }
                                TYPE_ADMIN -> { Admin }
                                else -> { RegularUser }
                            }

                            if (userSignInParams.password != password) continuation.resumeWithException(SignInException(message = "incorrect password"))

                            val user = User(id = it.id,
                                userParams = UserParams(name = name, email = email, dailyCalories = dailyCalories, type = type))

                            if(continuation.isActive) continuation.resume(user) { throw SignInException() }
                        }
                    } else { continuation.resumeWithException(SignInException(message = "user with email: ${userSignInParams.email} not found")) }
                }
                .addOnFailureListener { continuation.resumeWithException(SignInException(message = "user with email: ${userSignInParams.email} not found")) }
        }
    }
}