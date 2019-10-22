package com.example.inputofcalories.repo.regularflow

import com.example.inputofcalories.common.exception.MealException
import com.example.inputofcalories.entity.MealParams
import com.example.inputofcalories.repo.db.FirebaseDataBaseCollectionNames
import com.example.inputofcalories.repo.regularflow.model.MealFirebase
import com.google.firebase.firestore.FirebaseFirestore
import io.reactivex.Completable

class EditMealRepoImpl(
    private val firestore: FirebaseFirestore
): EditMealRepo {
    override fun edit(mealId: String, params: MealParams): Completable {
        return Completable.create{ emitter ->
            firestore.collection(FirebaseDataBaseCollectionNames.USERS).get()
                .addOnSuccessListener { userDocumentsQuerySnapshot ->
                    userDocumentsQuerySnapshot.forEach { queryDocumentSnapshot ->
                        val mealFirebase = MealFirebase(
                            calories = params.calories,
                            text = params.text,
                            weight = params.weight
                        )

                        queryDocumentSnapshot.reference
                            .collection(FirebaseDataBaseCollectionNames.MEALS)
                            .document(mealId)
                            .set(mealFirebase)
                            .addOnSuccessListener { emitter.onComplete() }
                            .addOnFailureListener { emitter.onError(MealException(error = it)) }
                    }
                }
                .addOnFailureListener { emitter.onError(MealException(error = it)) }
        }
    }
}