package com.example.inputofcalories.repo.regularflow

import com.example.inputofcalories.common.exception.MealException
import com.example.inputofcalories.entity.presentation.regular.*
import com.example.inputofcalories.repo.db.FirebaseDataBaseCollectionNames
import com.example.inputofcalories.repo.regularflow.model.MealFirebase
import com.example.inputofcalories.repo.service.datetime.DateTimeGeneratorService
import com.example.inputofcalories.repo.service.datetime.MONTH
import com.example.inputofcalories.repo.service.datetime.YEAR
import com.example.inputofcalories.repo.service.uuidgenerator.UUIDGeneratorService
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.suspendCancellableCoroutine
import java.util.*
import kotlin.coroutines.resumeWithException

/**
 *  users/.../meals/yyyy/mm/dd/...
 *
 *  e.g
 *
 *  users/.../collection:meals --- document:2020
 *                                             |
 *                                             -- collection:April
 *                                                               |
 *                                                               -- document:30:3773299f-c7ec-4485-87b3-b555a2f9ac2c (* meal id *)
 *                                                               -- document:30:3773299f-c7ec-4485-87b3-b555a2f9ac2c (* meal id *)
 *                                                               -- document:30:3773299f-c7ec-4485-87b3-b555a2f9ac2c (* meal id *)
 *                                                               -- document:29:3773299f-c7ec-4485-87b3-b555a2f9ac2c (* meal id *)
 *                                                               -- document:29:3773299f-c7ec-4485-87b3-b555a2f9ac2c (* meal id *)
 *                                                               -- document:29:3773299f-c7ec-4485-87b3-b555a2f9ac2c (* meal id *)
 */

class UserMealsFirestore(
    private val firestore: FirebaseFirestore,
    private val uuidGenerator: UUIDGeneratorService,
    private val timeFormatterService: DateTimeGeneratorService
): UserMealsRepo {

    override suspend fun addMeal(
        userId: String,
        params: MealParams,
        filterParams: MealFilterParams) {

        val pathPrefix = timeFormatterService.getAsPath(Date())
        val mId = "$pathPrefix:${uuidGenerator.get()}"

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

    //TODO need to setup onLoadMore(), where do we want to have state, that is the question
    @ExperimentalCoroutinesApi
    override suspend fun getMeals(uId: String): List<Meal> {
        val date = timeFormatterService.getByDate(Date())

        return suspendCancellableCoroutine { continuation ->
            firestore.collection(FirebaseDataBaseCollectionNames.USERS)
                .document(uId)
                .get()
                .addOnSuccessListener { queryDocumentSnapshot ->
                    queryDocumentSnapshot
                        .reference
                        .collection("${FirebaseDataBaseCollectionNames.MEALS}/${date[YEAR]}/${date[MONTH]}")
                        .get()
                        .addOnSuccessListener { mealQuerySnapshot ->
                            val meals: List<Meal> = mealQuerySnapshot.map { mealDocumentsSnapshot ->
                                val mealFirebase = mealDocumentsSnapshot.toObject(MealFirebase::class.java)

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
                            }

                            continuation.resume(meals) { throw MealException() }
                        }
                        .addOnFailureListener { continuation.resumeWithException(MealException()) }
                }
                .addOnFailureListener { continuation.resumeWithException(MealException()) }
        }
    }
}