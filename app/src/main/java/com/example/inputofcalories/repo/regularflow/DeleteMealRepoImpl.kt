package com.example.inputofcalories.repo.regularflow

import com.example.inputofcalories.common.exception.MealException
import com.example.inputofcalories.entity.presentation.regular.MealDeleteParams
import com.example.inputofcalories.repo.db.FirebaseDataBaseCollectionNames.MEALS
import com.example.inputofcalories.repo.db.FirebaseDataBaseCollectionNames.USERS
import com.google.firebase.firestore.FirebaseFirestore
import io.reactivex.Completable

class DeleteMealRepoImpl(
    private val firestore: FirebaseFirestore
): DeleteMealRepo {
    override fun delete(mealDeleteParams: MealDeleteParams): Completable {
        return Completable.create { emitter ->
            firestore.collection(USERS).get()
                .addOnSuccessListener { userQuerySnapshot ->
                    userQuerySnapshot.filter { it.id == mealDeleteParams.userId }.map { userDocumentQuerySnapshot ->

                        userDocumentQuerySnapshot.reference
                            .collection(MEALS)
                            .document(mealDeleteParams.mealId)
                            .delete()
                            .addOnSuccessListener { emitter.onComplete() }
                            .addOnFailureListener { error -> emitter.onError(MealException(error = error)) }
                    }
                }
                .addOnFailureListener { error -> emitter.onError(MealException(error = error)) }
        }
    }
}