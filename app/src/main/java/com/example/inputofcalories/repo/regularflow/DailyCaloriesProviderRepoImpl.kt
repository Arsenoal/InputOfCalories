package com.example.inputofcalories.repo.regularflow

import com.example.inputofcalories.repo.auth.registration.model.UserFirebase
import com.example.inputofcalories.repo.db.FirebaseDataBaseCollectionNames.USERS
import com.google.firebase.firestore.FirebaseFirestore
import io.reactivex.Single

class DailyCaloriesProviderRepoImpl(
    private val firestore: FirebaseFirestore
): DailyCaloriesProviderRepo {
    override fun provide(userId: String): Single<String> {
        return Single.create<String> { emitter ->
            firestore.collection(USERS)
                .document(userId)
                .get()
                .addOnSuccessListener { documentSnapshot ->
                    val userFirebase = documentSnapshot.toObject(UserFirebase::class.java)

                    userFirebase?.let {
                        emitter.onSuccess(it.dailyCalories)
                    }
                }
                .addOnFailureListener { error -> emitter.onError(error) }
        }
    }
}