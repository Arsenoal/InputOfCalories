package com.example.inputofcalories.repo.regularflow

import com.example.inputofcalories.common.exception.MealException
import com.example.inputofcalories.entity.presentation.regular.Meal
import com.example.inputofcalories.repo.db.FirebaseDataBaseCollectionNames
import com.example.inputofcalories.repo.regularflow.model.MealFirebase
import com.google.firebase.firestore.FirebaseFirestore
import io.reactivex.Completable

class EditMealRepoImpl(
    private val firestore: FirebaseFirestore
): EditMealRepo {
    override fun edit(meal: Meal): Completable {
        return Completable.create{ emitter ->
            firestore.collection(FirebaseDataBaseCollectionNames.USERS).get()
                .addOnSuccessListener { userDocumentsQuerySnapshot ->
                    userDocumentsQuerySnapshot.forEach { queryDocumentSnapshot ->
                        val mealFirebase = MealFirebase(
                            calories = meal.params.calories,
                            text = meal.params.text,
                            weight = meal.params.weight,
                            day = meal.filterParams.date.dayOfMonth,
                            month = meal.filterParams.date.month,
                            year = meal.filterParams.date.year,
                            from = meal.filterParams.time.from,
                            to = meal.filterParams.time.to)

                        queryDocumentSnapshot.reference
                            .collection(FirebaseDataBaseCollectionNames.MEALS)
                            .document(meal.id)
                            .set(mealFirebase)
                            .addOnSuccessListener { emitter.onComplete() }
                            .addOnFailureListener { emitter.onError(MealException(error = it)) }
                    }
                }
                .addOnFailureListener { emitter.onError(MealException(error = it)) }
        }
    }
}