package com.example.inputofcalories.repo.managerflow

import com.example.inputofcalories.common.exception.UserException
import com.example.inputofcalories.repo.auth.model.TYPE_MANAGER
import com.example.inputofcalories.repo.auth.model.UserFirebase
import com.example.inputofcalories.repo.db.FirebaseDataBaseCollectionNames.USERS
import com.google.firebase.firestore.FirebaseFirestore

class UpgradeUserToManagerFirestore(
    private val firestore: FirebaseFirestore
): UpgradeUserToManagerRepo {
    override suspend fun upgrade(userId: String) {
        firestore.collection(USERS).get()
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

                        firestore.collection(USERS)
                            .document(userId)
                            .set(upgradedUser)
                            .addOnFailureListener { throw UserException(error = it) }
                        }
                }
                .addOnFailureListener { throw UserException(error = it) }
    }
}