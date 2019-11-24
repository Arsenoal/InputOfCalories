package com.example.inputofcalories.repo.regularflow

import com.example.inputofcalories.common.exception.UserDailyCaloriesUpdateException
import com.example.inputofcalories.repo.auth.registration.model.UserFirebase
import com.example.inputofcalories.repo.db.FirebaseDataBaseCollectionNames.USERS
import com.google.firebase.firestore.FirebaseFirestore

class UpdateUsersDailyCaloriesFirestore(
    private val firestore: FirebaseFirestore
): UpdateUsersDailyCaloriesRepo {
    override suspend fun update(userId: String, dailyCalories: String) {
        firestore.collection(USERS).get()
            .addOnSuccessListener { usersQuerySnapshot ->
                usersQuerySnapshot.filter { it.id == userId }.map {
                    val userFirebase = it.toObject(UserFirebase::class.java)

                    val userUpdateFirebase = UserFirebase(
                        id = userFirebase.id,
                        name = userFirebase.name,
                        email = userFirebase.email,
                        dailyCalories = dailyCalories,
                        password = userFirebase.password,
                        type = userFirebase.type
                    )

                    firestore.collection(USERS)
                        .document(userId)
                        .set(userUpdateFirebase)
                        .addOnFailureListener { throw UserDailyCaloriesUpdateException(error = it) }
                    }
                }
                .addOnFailureListener { throw UserDailyCaloriesUpdateException(error = it) }
    }
}