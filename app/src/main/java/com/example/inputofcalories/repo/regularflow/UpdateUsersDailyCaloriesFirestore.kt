package com.example.inputofcalories.repo.regularflow

import com.example.inputofcalories.common.exception.UserDailyCaloriesUpdateException
import com.example.inputofcalories.repo.auth.model.UserFirebase
import com.example.inputofcalories.repo.db.FirebaseDataBaseCollectionNames.USERS
import com.google.firebase.firestore.FirebaseFirestore

class UpdateUsersDailyCaloriesFirestore(
    private val firestore: FirebaseFirestore
): UpdateUsersDailyCaloriesRepo {
    override suspend fun update(userId: String, dailyCalories: String) {
        firestore.collection(USERS).get()
            .addOnSuccessListener { usersQuerySnapshot ->
                usersQuerySnapshot
                    .filter { it.id == userId }
                    .map {
                        val userFirebase = it.toObject(UserFirebase::class.java)

                        val userUpdateFirebase = with(userFirebase) {
                            UserFirebase(
                                id = id,
                                name = name,
                                email = email,
                                dailyCalories = dailyCalories,
                                password = password,
                                type = type
                            )
                        }

                    firestore.collection(USERS)
                        .document(userId)
                        .set(userUpdateFirebase)
                        .addOnFailureListener { error -> throw UserDailyCaloriesUpdateException(error = error) }
                    }
                }
                .addOnFailureListener { throw UserDailyCaloriesUpdateException(error = it) }
    }
}