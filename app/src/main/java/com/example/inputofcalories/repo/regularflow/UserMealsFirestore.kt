package com.example.inputofcalories.repo.regularflow

import com.example.inputofcalories.common.exception.MealException
import com.example.inputofcalories.entity.presentation.regular.*
import com.example.inputofcalories.repo.db.FirebaseDataBaseCollectionNames
import com.example.inputofcalories.repo.regularflow.model.MealFirebase
import com.example.inputofcalories.repo.service.datetime.DAY
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

        val calendar = Calendar.getInstance()
        filterParams.date.run { calendar.set(year.toInt(), month.toInt(), dayOfMonth.toInt()) }

        val pathPrefix = timeFormatterService.getAsPath(calendar.time)
        val mId = "$pathPrefix:${uuidGenerator.get()}"

        val mealFirebase = MealFirebase(
            calories = params.calories, text = params.text, weight = params.weight,
            day = filterParams.date.dayOfMonth, month = filterParams.date.month, year = filterParams.date.year,
            from = filterParams.time.from, to = filterParams.time.to)

        val collectionPath = "${FirebaseDataBaseCollectionNames.USERS}/${FirebaseDataBaseCollectionNames.MEALS}/$mId"

        firestore.collection(collectionPath)
            .document(mId)
            .set(mealFirebase)
            .addOnFailureListener { throw MealException(error = it) }
    }

    override suspend fun deleteMeal(userId: String, mealDeleteParams: MealDeleteParams) {
        val calendar = Calendar.getInstance()
        mealDeleteParams.date.run { calendar.set(year.toInt(), month.toInt(), dayOfMonth.toInt()) }
        val dateFormatted = timeFormatterService.getByDate(calendar.time)

        val collectionPath
                = "${FirebaseDataBaseCollectionNames.USERS}/$userId/${FirebaseDataBaseCollectionNames.MEALS}/${dateFormatted[YEAR]}/${dateFormatted[MONTH]}"

        firestore
            .collection(collectionPath)
            .document(mealDeleteParams.mealId)
            .delete()
            .addOnFailureListener { throw MealException(error = it) }
    }

    override suspend fun editMeal(userId: String, meal: Meal) {
        val calendar = Calendar.getInstance()
        meal.filterParams.date.run { calendar.set(year.toInt(), month.toInt(), dayOfMonth.toInt()) }
        val dateFormatted = timeFormatterService.getByDate(calendar.time)

        val collectionPath
                = "${FirebaseDataBaseCollectionNames.USERS}/$userId/${FirebaseDataBaseCollectionNames.MEALS}/${dateFormatted[YEAR]}/${dateFormatted[MONTH]}"

        firestore
            .collection(collectionPath)
            .document(meal.id)
            .set(meal.toMealFirebase())
            .addOnFailureListener { throw MealException(error = it) }

    }

    @ExperimentalCoroutinesApi
    override suspend fun loadMeals(uId: String): List<Meal> {
        val date = Date()
        val dateFormatted = timeFormatterService.getByDate(date)

        val day = Calendar.getInstance().get(Calendar.DAY_OF_MONTH).toString()

        val collectionPath
                = "${FirebaseDataBaseCollectionNames.USERS}/$uId/${FirebaseDataBaseCollectionNames.MEALS}/${dateFormatted[YEAR]}/${dateFormatted[MONTH]}"

        return suspendCancellableCoroutine { continuation ->
            firestore.collection(collectionPath)
                .orderBy("day")
                .startAt(day)
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
    }

    @ExperimentalCoroutinesApi
    override suspend fun loadMealsByDate(uId: String, date: Date): List<Meal> {
        return listOf()
    }

    @ExperimentalCoroutinesApi
    override suspend fun loadMoreMeals(uId: String, date: Date): List<Meal> {
        val dateFormatted: Map<Int, String> = timeFormatterService.getByDate(date)
        val day: String = dateFormatted[DAY] ?: ""

        val collectionPath
                = "${FirebaseDataBaseCollectionNames.USERS}/$uId/${FirebaseDataBaseCollectionNames.MEALS}/${dateFormatted[YEAR]}/${dateFormatted[MONTH]}"

        return suspendCancellableCoroutine { continuation ->
            firestore
                .collection(collectionPath)
                .orderBy("day")
                .startAt(day)
                .endAt("${day}\uf8ff")
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
    }
}