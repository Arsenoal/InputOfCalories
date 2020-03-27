package com.example.inputofcalories.repo.managerflow

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

class ManagerFirestore(
    private val firestore: FirebaseFirestore
): ManagerRepo {
    override suspend fun downgradeToRegular(userId: String) {
        firestore.collection(FirebaseDataBaseCollectionNames.USERS).get()
            .addOnSuccessListener { usersQuerySnapshot ->
                usersQuerySnapshot.filter { documentSnapshot ->
                    userId == documentSnapshot.id
                }.map { documentSnapshot ->
                    val userFirebase = documentSnapshot.toObject(UserFirebase::class.java)

                    val downgradedUser = with(userFirebase) {
                        UserFirebase(
                            id = id,
                            name = name,
                            email = email,
                            password = password,
                            dailyCalories = dailyCalories,
                            type = TYPE_REGULAR
                        )
                    }

                    firestore.collection(FirebaseDataBaseCollectionNames.USERS)
                        .document(userId)
                        .set(downgradedUser)
                        .addOnFailureListener { throw UserException(error = it) }
                }
            }
            .addOnFailureListener { throw UserException(error = it) }
    }

    override suspend fun upgradeToManager(userId: String) {
        firestore.collection(FirebaseDataBaseCollectionNames.USERS).get()
            .addOnSuccessListener { usersQuerySnapshot ->
                usersQuerySnapshot
                    .filter { documentSnapshot -> userId == documentSnapshot.id }
                    .map { documentSnapshot ->
                        val userFirebase = documentSnapshot.toObject(UserFirebase::class.java)

                        val upgradedUser = with(userFirebase) {
                            UserFirebase(
                                id = id,
                                name = name,
                                email = email,
                                password = password,
                                dailyCalories = dailyCalories,
                                type = TYPE_MANAGER
                            )
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
    override suspend fun getUsers(userId: String): List<User> {
        return suspendCancellableCoroutine { continuation ->
            firestore.collection(FirebaseDataBaseCollectionNames.USERS).get()
                .addOnSuccessListener { usersQuery ->
                    val users: List<User> = usersQuery.documents
                        .asSequence()
                        .filter {
                            val userFirebase = it.toObject(UserFirebase::class.java)

                            var userType = -1

                            userFirebase?.let { user->
                                userType = user.type
                            }

                            userId != it.id && (userType == TYPE_REGULAR || userType == TYPE_MANAGER)
                        }
                        .map { documentSnapshot ->
                            val userFirebase = documentSnapshot.toObject(UserFirebase::class.java)

                            var user = User(
                                documentSnapshot.id,
                                UserParams(String.empty(), String.empty(), String.empty(), RegularUser)
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

                    continuation.resume(users) {
                        throw UserException(it)
                    }
                }
                .addOnFailureListener { continuation.resumeWithException(UserException(it)) }
        }
    }
}