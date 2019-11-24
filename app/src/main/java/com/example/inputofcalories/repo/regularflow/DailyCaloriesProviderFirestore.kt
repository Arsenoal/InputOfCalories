package com.example.inputofcalories.repo.regularflow

import com.example.inputofcalories.common.exception.UserDailyCaloriesException
import com.example.inputofcalories.repo.auth.registration.model.UserFirebase
import com.example.inputofcalories.repo.db.FirebaseDataBaseCollectionNames.USERS
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resumeWithException

class DailyCaloriesProviderFirestore(
    private val firestore: FirebaseFirestore
): DailyCaloriesProviderRepo {

    @ExperimentalCoroutinesApi
    override suspend fun provide(userId: String): String {
        return suspendCancellableCoroutine { continuation ->
            firestore.collection(USERS)
                .document(userId)
                .get()
                .addOnSuccessListener { documentSnapshot ->
                    val userFirebase = documentSnapshot.toObject(UserFirebase::class.java)

                    userFirebase?.let {
                        continuation.resume(it.dailyCalories) {
                            throw UserDailyCaloriesException()
                        }
                    }
                }
                .addOnFailureListener { continuation.resumeWithException(UserDailyCaloriesException(error = it)) }
        }
    }
}