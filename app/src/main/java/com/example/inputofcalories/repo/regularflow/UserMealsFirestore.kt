package com.example.inputofcalories.repo.regularflow

import com.example.inputofcalories.common.exception.MealException
import com.example.inputofcalories.common.logger.IOCLogger
import com.example.inputofcalories.entity.presentation.regular.*
import com.example.inputofcalories.repo.db.FirebaseDataBaseCollectionNames
import com.example.inputofcalories.repo.regularflow.model.MealFirebase
import com.example.inputofcalories.repo.service.UUIDGeneratorService
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resumeWithException

class UserMealsFirestore(
    private val firestore: FirebaseFirestore,
    private val uuidGenerator: UUIDGeneratorService
): UserMealsRepo {

    private var tag = UserMealsFirestore::class.java.name

    override suspend fun addMeal(
        userId: String,
        params: MealParams,
        filterParams: MealFilterParams) {

        val mId = uuidGenerator.get()

        firestore.collection(FirebaseDataBaseCollectionNames.USERS)
            .document(userId)
            .get()
            .addOnSuccessListener {
                val mealFirebase = MealFirebase(
                    calories = params.calories, text = params.text, weight = params.weight,
                    day = filterParams.date.dayOfMonth, month = filterParams.date.month, year = filterParams.date.year,
                    from = filterParams.time.from, to = filterParams.time.to)

                it.reference
                    .collection(FirebaseDataBaseCollectionNames.MEALS)
                    .document(mId)
                    .set(mealFirebase)
                    .addOnFailureListener { error -> throw MealException(error = error) }
            }
            .addOnFailureListener { throw MealException(error = it) }
    }

    override suspend fun deleteMeal(mealDeleteParams: MealDeleteParams) {
        firestore.collection(FirebaseDataBaseCollectionNames.USERS).get()
            .addOnSuccessListener { userQuerySnapshot ->
                userQuerySnapshot.filter { it.id == mealDeleteParams.userId }.map { userDocumentQuerySnapshot ->

                    userDocumentQuerySnapshot.reference
                        .collection(FirebaseDataBaseCollectionNames.MEALS)
                        .document(mealDeleteParams.mealId)
                        .delete()
                        .addOnFailureListener { throw MealException(error = it) }
                }
            }
            .addOnFailureListener { throw MealException(error = it) }
    }

    override suspend fun editMeal(meal: Meal) {
        firestore.collection(FirebaseDataBaseCollectionNames.USERS).get()
            .addOnSuccessListener { userDocumentsQuerySnapshot ->
                userDocumentsQuerySnapshot.forEach { queryDocumentSnapshot ->
                    val mealFirebase = with(meal) {
                        MealFirebase(
                            calories = params.calories, text = params.text, weight = params.weight,
                            day = filterParams.date.dayOfMonth, month = filterParams.date.month, year = filterParams.date.year,
                            from = filterParams.time.from, to = filterParams.time.to)
                    }

                    queryDocumentSnapshot.reference
                        .collection(FirebaseDataBaseCollectionNames.MEALS)
                        .document(meal.id)
                        .set(mealFirebase)
                        .addOnFailureListener { throw MealException(error = it) }
                }
            }
            .addOnFailureListener { throw MealException(error = it) }
    }

    @ExperimentalCoroutinesApi
    override suspend fun getMeals(uId: String): List<Meal> {
        return suspendCancellableCoroutine { continuation ->
            firestore.collection(FirebaseDataBaseCollectionNames.USERS)
                .document(uId)
                .get()
                .addOnSuccessListener { queryDocumentSnapshot ->
                    queryDocumentSnapshot
                        .reference
                        .collection(FirebaseDataBaseCollectionNames.MEALS)
                        .get()
                        .addOnSuccessListener { mealQuerySnapshot ->
                            val list: List<Meal> = mealQuerySnapshot.map { mealDocumentsSnapshot ->
                                val mealFirebase: MealFirebase = mealDocumentsSnapshot.toObject(MealFirebase::class.java)

                                val mealTimeParams = when (mealFirebase.from) {
                                    BreakfastTime.from -> { BreakfastTime }
                                    LunchTime.from -> { LunchTime }
                                    DinnerTime.from -> { DinnerTime }
                                    SnackTime.from -> { SnackTime }
                                    else -> { LunchTime }
                                }

                                val mealParams = with(mealFirebase) {
                                    MealParams(text = text, calories = calories, weight = weight)
                                }

                                val mealFilterParams = with(mealFirebase) {
                                    MealFilterParams(date = MealDateParams(year = year, month = month, dayOfMonth = day), time = mealTimeParams)
                                }

                                val meal = Meal(id = mealDocumentsSnapshot.id, params = mealParams, filterParams = mealFilterParams)

                                meal
                            }.toList()

                            list.forEach { meal -> IOCLogger.d(tag, meal.toString()) }

                            continuation.resume(list) { throw MealException() }
                        }
                        .addOnFailureListener { error -> continuation.resumeWithException(MealException(error = error)) }
                }
                .addOnFailureListener { error -> continuation.resumeWithException(MealException(error = error)) }
        }
    }
}