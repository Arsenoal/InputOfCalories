package com.example.inputofcalories.repo.regularflow

import com.example.inputofcalories.common.exception.UserDailyCaloriesUpdateException
import com.example.inputofcalories.repo.auth.registration.model.UserFirebase
import com.example.inputofcalories.repo.db.FirebaseDataBaseCollectionNames.USERS
import com.google.firebase.firestore.FirebaseFirestore
import io.reactivex.Completable

class UpdateUsersDailyCaloriesRepoImpl(
    private val firestore: FirebaseFirestore
): UpdateUsersDailyCaloriesRepo {
    override fun update(userId: String, dailyCalories: String): Completable {
        return Completable.create { emitter ->
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
                            .addOnSuccessListener { emitter.onComplete() }
                            .addOnFailureListener { error -> emitter.onError(UserDailyCaloriesUpdateException(error = error)) }
                    }
                }
                .addOnFailureListener { emitter.onError(UserDailyCaloriesUpdateException(error = it)) }
        }
    }
}