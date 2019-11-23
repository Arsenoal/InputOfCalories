package com.example.inputofcalories.repo.adminflow

import com.example.inputofcalories.common.exception.UserException
import com.example.inputofcalories.repo.auth.registration.model.TYPE_ADMIN
import com.example.inputofcalories.repo.auth.registration.model.TYPE_MANAGER
import com.example.inputofcalories.repo.auth.registration.model.TYPE_REGULAR
import com.example.inputofcalories.repo.auth.registration.model.UserFirebase
import com.example.inputofcalories.repo.db.FirebaseDataBaseCollectionNames
import com.google.firebase.firestore.FirebaseFirestore

class AdminUserStatusManipulatorFirestore(
    private val firestore: FirebaseFirestore
): AdminUserStatusManipulatorRepo {

    override suspend fun downgradeToManager(userId: String) {
        firestore.collection(FirebaseDataBaseCollectionNames.USERS).get()
            .addOnSuccessListener { usersQuerySnapshot ->
                usersQuerySnapshot.filter { documentSnapshot ->
                    userId == documentSnapshot.id
                }.map { documentSnapshot ->
                    val userFirebase = documentSnapshot.toObject(UserFirebase::class.java)

                    val downgradedUser = UserFirebase(
                        id = userFirebase.id,
                        name = userFirebase.name,
                        email = userFirebase.email,
                        password = userFirebase.password,
                        dailyCalories = userFirebase.dailyCalories,
                        type = TYPE_MANAGER
                    )

                    firestore.collection(FirebaseDataBaseCollectionNames.USERS)
                        .document(userId)
                        .set(downgradedUser)
                        .addOnFailureListener { throw UserException(error = it) }
                }
            }
            .addOnFailureListener { throw UserException(error = it) }
    }

    override suspend fun downgradeToRegular(userId: String) {
        firestore.collection(FirebaseDataBaseCollectionNames.USERS).get()
            .addOnSuccessListener { usersQuerySnapshot ->
                usersQuerySnapshot.filter { documentSnapshot ->
                    userId == documentSnapshot.id
                }.map { documentSnapshot ->
                    val userFirebase = documentSnapshot.toObject(UserFirebase::class.java)

                    val downgradedUser = UserFirebase(
                        id = userFirebase.id,
                        name = userFirebase.name,
                        email = userFirebase.email,
                        password = userFirebase.password,
                        dailyCalories = userFirebase.dailyCalories,
                        type = TYPE_REGULAR
                    )

                    firestore.collection(FirebaseDataBaseCollectionNames.USERS)
                        .document(userId)
                        .set(downgradedUser)
                        .addOnFailureListener { throw UserException(error = it) }
                }
            }
            .addOnFailureListener { throw UserException(error = it) }
    }

    override suspend fun upgradeToAdmin(userId: String) {
        firestore.collection(FirebaseDataBaseCollectionNames.USERS).get()
            .addOnSuccessListener { usersQuerySnapshot ->
                usersQuerySnapshot.filter { documentSnapshot ->
                    userId == documentSnapshot.id
                }.map { documentSnapshot ->
                    val userFirebase = documentSnapshot.toObject(UserFirebase::class.java)

                    val upgradedUser = UserFirebase(
                        id = userFirebase.id,
                        name = userFirebase.name,
                        email = userFirebase.email,
                        password = userFirebase.password,
                        dailyCalories = userFirebase.dailyCalories,
                        type = TYPE_ADMIN
                    )

                    firestore.collection(FirebaseDataBaseCollectionNames.USERS)
                        .document(userId)
                        .set(upgradedUser)
                        .addOnFailureListener { throw UserException(error = it) }
                }
            }
            .addOnFailureListener { throw UserException(error = it) }
    }

    override suspend fun upgradeToManager(userId: String) {
        firestore.collection(FirebaseDataBaseCollectionNames.USERS).get()
            .addOnSuccessListener { usersQuerySnapshot ->
                usersQuerySnapshot.filter { documentSnapshot ->
                    userId == documentSnapshot.id
                }.map { documentSnapshot ->
                    val userFirebase = documentSnapshot.toObject(UserFirebase::class.java)

                    val upgradedUser = UserFirebase(
                        id = userFirebase.id,
                        name = userFirebase.name,
                        email = userFirebase.email,
                        password = userFirebase.password,
                        dailyCalories = userFirebase.dailyCalories,
                        type = TYPE_MANAGER)

                    firestore.collection(FirebaseDataBaseCollectionNames.USERS)
                        .document(userId)
                        .set(upgradedUser)
                        .addOnFailureListener { throw UserException(error = it) }
                }
            }
            .addOnFailureListener { throw UserException(error = it) }
    }
}