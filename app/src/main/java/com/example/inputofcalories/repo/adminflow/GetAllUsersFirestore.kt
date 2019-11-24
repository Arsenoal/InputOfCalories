package com.example.inputofcalories.repo.adminflow

import com.example.inputofcalories.common.exception.UserException
import com.example.inputofcalories.common.extensions.empty
import com.example.inputofcalories.entity.register.*
import com.example.inputofcalories.repo.auth.registration.model.TYPE_ADMIN
import com.example.inputofcalories.repo.auth.registration.model.TYPE_MANAGER
import com.example.inputofcalories.repo.auth.registration.model.UserFirebase
import com.example.inputofcalories.repo.db.FirebaseDataBaseCollectionNames.USERS
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resumeWithException

class GetAllUsersFirestore(
    private val firestore: FirebaseFirestore
): GetAllUsersRepo {
    @ExperimentalCoroutinesApi
    override suspend fun get(userId: String): List<User> =
            suspendCancellableCoroutine { continuation ->
                firestore.collection(USERS).get()
                    .addOnSuccessListener { usersQuery ->
                        val users: List<User> = usersQuery.documents
                            .filter { userId != it.id }
                            .map { documentSnapshot ->
                                val userFirebase = documentSnapshot.toObject(UserFirebase::class.java)

                                var user = User(
                                    documentSnapshot.id,
                                    UserParams(
                                        name = String.empty(),
                                        email = String.empty(),
                                        dailyCalories = String.empty(),
                                        type = RegularUser)
                                )

                                userFirebase?.run {
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

                                    user = User(
                                        documentSnapshot.id,
                                        userParams)
                                }

                                user
                            }
                            .toList()

                        continuation.resume(users) { throw UserException(message = "users get cancelled") }
                    }
                    .addOnFailureListener {
                        continuation.resumeWithException(UserException(it))
                    }
            }
}