package com.example.inputofcalories.repo.regularflow.dailycalories

import com.example.inputofcalories.common.exception.UserDailyCaloriesException
import com.example.inputofcalories.common.exception.UserDailyCaloriesUpdateException
import com.example.inputofcalories.repo.auth.model.UserFirebase
import com.example.inputofcalories.repo.db.FirebaseDataBaseCollectionNames
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resumeWithException

class DailyCaloriesFirestore(
    private val firestore: FirebaseFirestore
): DailyCaloriesRepo {

    @ExperimentalCoroutinesApi
    override suspend fun getDailyCaloriesLimit(userId: String): String {
        return suspendCancellableCoroutine { continuation ->
            firestore.collection(FirebaseDataBaseCollectionNames.USERS)
                .document(userId)
                .get()
                .addOnSuccessListener { documentSnapshot ->
                    documentSnapshot.toObject(UserFirebase::class.java)?.run {
                        val res = if(dailyCalories.isNotBlank()) dailyCalories else "0"
                        continuation.resume(res) { throw UserDailyCaloriesException() }
                    }
                }
                .addOnFailureListener { error -> continuation.resumeWithException(UserDailyCaloriesException(error = error)) }
        }
    }

    //TODO pass continuation as function argument
    override suspend fun updateDailyCaloriesLimit(userId: String, dailyCaloriesLimit: String) {
        firestore.collection(FirebaseDataBaseCollectionNames.USERS).get()
            .addOnSuccessListener { usersQuerySnapshot ->
                usersQuerySnapshot
                    .filter { it.id == userId }
                    .map {
                        val userFirebase = it.toObject(UserFirebase::class.java)

                        val userUpdateFirebase = with(userFirebase) {
                            UserFirebase(id = id, name = name, email = email, dailyCalories = dailyCalories, password = password, type = type
                            )
                        }

                        firestore.collection(FirebaseDataBaseCollectionNames.USERS)
                            .document(userId)
                            .set(userUpdateFirebase)
                            .addOnFailureListener { error -> throw UserDailyCaloriesUpdateException(error = error) }
                    }
            }
            .addOnFailureListener { error -> throw UserDailyCaloriesUpdateException(error = error) }
    }
}