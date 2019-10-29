package com.example.inputofcalories.repo.regularflow

import com.example.inputofcalories.common.exception.MealException
import com.example.inputofcalories.entity.presentation.regular.*
import com.example.inputofcalories.repo.db.FirebaseDataBaseCollectionNames.MEALS
import com.example.inputofcalories.repo.db.FirebaseDataBaseCollectionNames.USERS
import com.example.inputofcalories.repo.regularflow.model.MealFirebase
import com.google.firebase.firestore.FirebaseFirestore
import io.reactivex.Single

class MealsProviderRepoImpl(
    private val firestore: FirebaseFirestore
): MealsProviderRepo {
    override fun getMealsByUserId(uId: String): Single<List<Meal>> {
        return Single.create<List<Meal>> { emitter ->
            firestore.collection(USERS).get()
                .addOnSuccessListener { userQuerySnapshot ->
                    userQuerySnapshot.filter { uId == it.id }.forEach { queryDocumentSnapshot ->
                        queryDocumentSnapshot.reference.collection(MEALS).get()
                            .addOnSuccessListener { mealQuerySnapshot ->
                                val list: List<Meal> = mealQuerySnapshot.map { mealDocumentsSnapshot ->
                                    val mealFirebase: MealFirebase = mealDocumentsSnapshot.toObject(MealFirebase::class.java)

                                    val mealTimeParams = when(mealFirebase.from) {
                                        BreakfastTime.from -> { BreakfastTime }
                                        LunchTime.from -> { LunchTime }
                                        DinnerTime.from -> { DinnerTime }
                                        SnackTime.from -> { SnackTime }
                                        else -> { LunchTime }
                                    }

                                    //TODO move to mapper
                                    val mealParams = MealParams(
                                        text = mealFirebase.text,
                                        calories = mealFirebase.calories,
                                        weight = mealFirebase.weight)

                                    //TODO init via mapper
                                    val mealFilterParams = MealFilterParams(
                                        date = MealDateParams(
                                            year = mealFirebase.year,
                                            month = mealFirebase.month,
                                            dayOfMonth = mealFirebase.day),
                                        time = mealTimeParams)

                                    val meal = Meal(
                                        id = mealDocumentsSnapshot.id,
                                        params = mealParams,
                                        filterParams = mealFilterParams)

                                    meal
                                }.toList()

                                if(!emitter.isDisposed)
                                    emitter.onSuccess(list)
                            }
                            .addOnFailureListener { error ->
                                if(!emitter.isDisposed)
                                    emitter.onError(MealException(error = error))
                            }
                    }
                }
                .addOnFailureListener { error ->
                    if(!emitter.isDisposed)
                        emitter.onError(MealException(error = error))
                }
        }
    }
}