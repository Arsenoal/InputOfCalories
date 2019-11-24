package com.example.inputofcalories.repo.regularflow

import com.example.inputofcalories.common.exception.MealException
import com.example.inputofcalories.common.exception.UserException
import com.example.inputofcalories.entity.presentation.regular.MealFilterParams
import com.example.inputofcalories.entity.presentation.regular.MealParams
import com.example.inputofcalories.repo.common.service.UUIDGeneratorService
import com.example.inputofcalories.repo.db.FirebaseDataBaseCollectionNames.MEALS
import com.example.inputofcalories.repo.db.FirebaseDataBaseCollectionNames.USERS
import com.example.inputofcalories.repo.regularflow.model.MealFirebase
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resumeWithException

class AddMealFirestore(
    private val firestore: FirebaseFirestore,
    private val uuidGeneratorService: UUIDGeneratorService
): AddMealRepo {
    override suspend fun add(userId: String, params: MealParams, filterParams: MealFilterParams) {

        val mId = uuidGeneratorService.get().toString()

        firestore.collection(USERS)
            .document(userId).get()
            .addOnSuccessListener {
                val mealFirebase = MealFirebase(
                    calories = params.calories,
                    text = params.text,
                    weight = params.weight,
                    day = filterParams.date.dayOfMonth,
                    month = filterParams.date.month,
                    year = filterParams.date.year,
                    from = filterParams.time.from,
                    to = filterParams.time.to)

                it.reference
                    .collection(MEALS)
                    .document(mId)
                    .set(mealFirebase)
                    .addOnFailureListener { throw MealException(error = it) }
            }
            .addOnFailureListener { throw UserException(error = it) }
    }
}