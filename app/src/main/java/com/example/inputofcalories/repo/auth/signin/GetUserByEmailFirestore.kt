package com.example.inputofcalories.repo.auth.signin

import com.example.inputofcalories.common.exception.UserException
import com.example.inputofcalories.entity.register.*
import com.example.inputofcalories.repo.auth.registration.model.TYPE_ADMIN
import com.example.inputofcalories.repo.auth.registration.model.TYPE_MANAGER
import com.example.inputofcalories.repo.auth.registration.model.UserFirebase
import com.example.inputofcalories.repo.db.FirebaseDataBaseCollectionNames
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resumeWithException

class GetUserByEmailFirestore(
    private val firestore: FirebaseFirestore
): GetUserByEmailRepo {
    @ExperimentalCoroutinesApi
    override suspend fun get(email: String): User? {
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
                                    id = id,
                                    userParams = UserParams(
                                        name = name,
                                        email = email,
                                        dailyCalories = dailyCalories,
                                        type = type
                                    )
                                )
                            }

                            if (continuation.isActive) continuation.resume(user) { throw UserException(message = "user with email: $email not found") }
                        }

                    if (continuation.isActive) continuation.resumeWithException(UserException(message = "user with email: $email not found"))
                }
                .addOnFailureListener { error -> continuation.resumeWithException(UserException(message = error.message)) }
        }
    }
}