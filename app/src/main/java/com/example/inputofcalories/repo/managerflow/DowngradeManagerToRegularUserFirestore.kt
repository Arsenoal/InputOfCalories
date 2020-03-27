package com.example.inputofcalories.repo.managerflow

import com.example.inputofcalories.common.exception.UserException
import com.example.inputofcalories.repo.auth.model.TYPE_REGULAR
import com.example.inputofcalories.repo.auth.model.UserFirebase
import com.example.inputofcalories.repo.db.FirebaseDataBaseCollectionNames.USERS
import com.google.firebase.firestore.FirebaseFirestore

class DowngradeManagerToRegularUserFirestore(
    private val firestore: FirebaseFirestore
): DowngradeManagerToRegularUserRepo {
    override suspend fun downgrade(userId: String) {
        firestore.collection(USERS).get()
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

                    firestore.collection(USERS)
                        .document(userId)
                        .set(downgradedUser)
                        .addOnFailureListener { throw UserException(error = it) }
                }
            }
            .addOnFailureListener { throw UserException(error = it) }
    }
}