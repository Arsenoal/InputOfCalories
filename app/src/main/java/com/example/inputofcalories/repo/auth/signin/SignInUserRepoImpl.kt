package com.example.inputofcalories.repo.auth.signin

import com.example.inputofcalories.common.exception.SignInException
import com.example.inputofcalories.entity.register.*
import com.example.inputofcalories.repo.auth.registration.model.TYPE_ADMIN
import com.example.inputofcalories.repo.auth.registration.model.TYPE_MANAGER
import com.example.inputofcalories.repo.auth.registration.model.UserFirebase
import com.example.inputofcalories.repo.db.FirebaseDataBaseCollectionNames.USERS
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resumeWithException

class SignInUserRepoImpl(
    private val firestore: FirebaseFirestore
): SignInUserRepo {

    @ExperimentalCoroutinesApi
    override suspend fun signIn(userSignInParams: UserSignInParams): User {
        val isUserWithEmailPresent = checkUserWithEmailPresentInDb(userSignInParams.email)

        if (isUserWithEmailPresent) {
            return signInUser(userSignInParams)
        } else {
            throw SignInException(message = "user with provided email is not present in db")
        }
    }

    @ExperimentalCoroutinesApi
    private suspend fun checkUserWithEmailPresentInDb(email: String): Boolean {
        return suspendCancellableCoroutine { continuation ->
            val usersRef = firestore.collection(USERS)

            usersRef.get()
                .addOnSuccessListener { querySnapshot ->
                    querySnapshot.documents.forEach { documentSnapshot ->
                        val userFirebase = documentSnapshot.toObject(UserFirebase::class.java)

                        userFirebase?.let {
                            if(it.email == email) continuation.resume(true) {}
                        }
                    }

                    if(continuation.isActive) continuation.resume(false) {}
                }
                .addOnFailureListener { continuation.resume(false) {} }
        }
    }

    @ExperimentalCoroutinesApi
    private suspend fun signInUser(userSignInParams: UserSignInParams): User {
        return suspendCancellableCoroutine { continuation ->
            val usersRef = firestore.collection(USERS)

            usersRef.get()
                .addOnSuccessListener { querySnapshot ->
                    querySnapshot.documents.forEach { documentSnapshot ->
                        val userFirebase = documentSnapshot.toObject(UserFirebase::class.java)

                        userFirebase?.run {
                            if(email == userSignInParams.email && password == userSignInParams.password) {
                                val type: UserType = when(type) {
                                    TYPE_MANAGER -> { UserManager }
                                    TYPE_ADMIN -> { Admin }
                                    else -> { RegularUser }
                                }

                                val userParams = UserParams(
                                    name = name,
                                    email = email,
                                    dailyCalories = dailyCalories,
                                    type = type)

                                val user = User(
                                    id = documentSnapshot.id,
                                    userParams = userParams)

                                continuation.resume(user) {}
                            }
                        }
                    }

                    if(continuation.isCompleted) continuation.resumeWithException(SignInException(message = "password mismatch"))
                }
                .addOnFailureListener {
                    if(continuation.isCompleted) continuation.resumeWithException(SignInException(error = it, message = it.message))
                }
        }
    }
}