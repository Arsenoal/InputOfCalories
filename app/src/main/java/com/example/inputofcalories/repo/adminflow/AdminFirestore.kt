package com.example.inputofcalories.repo.adminflow

import com.example.inputofcalories.common.exception.UserException
import com.example.inputofcalories.common.extensions.empty
import com.example.inputofcalories.entity.register.*
import com.example.inputofcalories.repo.auth.model.TYPE_ADMIN
import com.example.inputofcalories.repo.auth.model.TYPE_MANAGER
import com.example.inputofcalories.repo.auth.model.TYPE_REGULAR
import com.example.inputofcalories.repo.auth.model.UserFirebase
import com.example.inputofcalories.repo.db.FirebaseDataBaseCollectionNames
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resumeWithException

class AdminFirestore(
    private val firestore: FirebaseFirestore
): AdminRepo {

    override suspend fun changeUserType(userId: String, newType: Int) {
        firestore.collection(FirebaseDataBaseCollectionNames.USERS).get()
            .addOnSuccessListener { usersQuerySnapshot ->
                usersQuerySnapshot.filter { documentSnapshot ->
                    userId == documentSnapshot.id
                }.map { documentSnapshot ->
                    val userFirebase = documentSnapshot.toObject(UserFirebase::class.java)

                    val upgradedUser = with(userFirebase) {
                        UserFirebase(id = id, name = name, email = email, password = password, dailyCalories = dailyCalories, type = newType)
                    }

                    firestore.collection(FirebaseDataBaseCollectionNames.USERS)
                        .document(userId)
                        .set(upgradedUser)
                        .addOnFailureListener { throw UserException(error = it) }
                }
            }
            .addOnFailureListener { throw UserException(error = it) }
    }

    @ExperimentalCoroutinesApi
    override suspend fun getUsers(userId: String): List<User> =
        suspendCancellableCoroutine { continuation ->
            firestore.collection(FirebaseDataBaseCollectionNames.USERS).get()
                .addOnSuccessListener { usersQuery ->
                    val users: List<User> = usersQuery.documents
                        .filter { userId != it.id }
                        .map { documentSnapshot ->
                            val userFirebase = documentSnapshot.toObject(UserFirebase::class.java)

                            var user = User(
                                documentSnapshot.id,
                                UserParams(name = String.empty(), email = String.empty(), dailyCalories = String.empty(), type = RegularUser)
                            )

                            userFirebase?.run {
                                val type: UserType = when(type) {
                                    TYPE_MANAGER -> { UserManager }
                                    TYPE_ADMIN -> { Admin }
                                    else -> { RegularUser }
                                }

                                val userParams = UserParams(name = name, email = email, dailyCalories = dailyCalories, type = type)

                                user = User(
                                    documentSnapshot.id,
                                    userParams)
                            }

                            user
                        }
                        .toList()

                    continuation.resume(users) { throw UserException(message = "users get cancelled") }
                }
                .addOnFailureListener { continuation.resumeWithException(UserException(it)) }
        }
}