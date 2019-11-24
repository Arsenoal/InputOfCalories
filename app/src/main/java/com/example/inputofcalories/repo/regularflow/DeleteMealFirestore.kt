package com.example.inputofcalories.repo.regularflow

import com.example.inputofcalories.common.exception.MealException
import com.example.inputofcalories.entity.presentation.regular.MealDeleteParams
import com.example.inputofcalories.repo.db.FirebaseDataBaseCollectionNames.MEALS
import com.example.inputofcalories.repo.db.FirebaseDataBaseCollectionNames.USERS
import com.google.firebase.firestore.FirebaseFirestore

class DeleteMealFirestore(
    private val firestore: FirebaseFirestore
): DeleteMealRepo {
    override suspend fun delete(mealDeleteParams: MealDeleteParams){

            firestore.collection(USERS).get()
                .addOnSuccessListener { userQuerySnapshot ->
                    userQuerySnapshot.filter { it.id == mealDeleteParams.userId }.map { userDocumentQuerySnapshot ->

                        userDocumentQuerySnapshot.reference
                            .collection(MEALS)
                            .document(mealDeleteParams.mealId)
                            .delete()
                            .addOnFailureListener { throw MealException(error = it) }
                    }
                }
                .addOnFailureListener { throw MealException(error = it) }
    }
}