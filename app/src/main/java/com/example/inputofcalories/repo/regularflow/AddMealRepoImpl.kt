package com.example.inputofcalories.repo.regularflow

import com.example.inputofcalories.common.exception.MealException
import com.example.inputofcalories.entity.presentation.regular.Meal
import com.example.inputofcalories.entity.presentation.regular.MealFilterParams
import com.example.inputofcalories.entity.presentation.regular.MealParams
import com.example.inputofcalories.repo.common.service.UUIDGeneratorService
import com.example.inputofcalories.repo.db.FirebaseDataBaseCollectionNames.MEALS
import com.example.inputofcalories.repo.db.FirebaseDataBaseCollectionNames.USERS
import com.example.inputofcalories.repo.regularflow.model.MealFirebase
import com.google.firebase.firestore.FirebaseFirestore
import io.reactivex.Completable

class AddMealRepoImpl(
    private val firestore: FirebaseFirestore,
    private val uuidGeneratorService: UUIDGeneratorService
): AddMealRepo {
    override fun add(params: MealParams, filterParams: MealFilterParams): Completable {
        return Completable.create{ emitter ->
            firestore.collection(USERS).get()
                .addOnSuccessListener { userDocumentsQuerySnapshot ->
                    userDocumentsQuerySnapshot.forEach { queryDocumentSnapshot ->
                        val mealFirebase = MealFirebase(
                            calories = params.calories,
                            text = params.text,
                            weight = params.weight,
                            day = filterParams.date.dayOfMonth,
                            month = filterParams.date.month,
                            year = filterParams.date.year,
                            from = filterParams.time.from,
                            to = filterParams.time.to)

                        val mId = uuidGeneratorService.get().toString()

                        queryDocumentSnapshot.reference
                            .collection(MEALS)
                            .document(mId)
                            .set(mealFirebase)
                            .addOnSuccessListener { emitter.onComplete() }
                            .addOnFailureListener { emitter.onError(MealException(error = it)) }
                    }
                }
                .addOnFailureListener { emitter.onError(MealException(error = it)) }
        }
    }
}