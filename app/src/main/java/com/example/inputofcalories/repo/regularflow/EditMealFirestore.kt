package com.example.inputofcalories.repo.regularflow

import com.example.inputofcalories.common.exception.MealException
import com.example.inputofcalories.entity.presentation.regular.Meal
import com.example.inputofcalories.repo.db.FirebaseDataBaseCollectionNames.MEALS
import com.example.inputofcalories.repo.db.FirebaseDataBaseCollectionNames.USERS
import com.example.inputofcalories.repo.regularflow.model.MealFirebase
import com.google.firebase.firestore.FirebaseFirestore

class EditMealFirestore(
    private val firestore: FirebaseFirestore
): EditMealRepo {
    override suspend fun edit(meal: Meal) {
            firestore.collection(USERS).get()
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
                            .collection(MEALS)
                            .document(meal.id)
                            .set(mealFirebase)
                            .addOnFailureListener { throw MealException(error = it) }
                    }
                }
                .addOnFailureListener { throw MealException(error = it) }
    }
}