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
                .addOnSuccessListener { userDocumentsQuerySnapshot ->
                    userDocumentsQuerySnapshot.forEach { queryDocumentSnapshot ->
                        queryDocumentSnapshot.reference.collection(MEALS).get()
                            .addOnSuccessListener { mealQuerySnapshot ->
                                val list: List<Meal> = mealQuerySnapshot.map { mealDocumentsSnapshot ->
                                    val mealFirebase: MealFirebase = mealDocumentsSnapshot.toObject(MealFirebase::class.java)

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
                                        time = LunchTime()
                                    )

                                    val meal = Meal(
                                        id = mealDocumentsSnapshot.id,
                                        params = mealParams,
                                        filterParams = mealFilterParams)

                                    meal
                                }.toList()

                                emitter.onSuccess(list)
                            }
                            .addOnFailureListener { emitter.onError(MealException(error = it)) }
                    }
                }
                .addOnFailureListener { emitter.onError(MealException(error = it)) }
        }
    }

    override fun getMealsByUserIdAndFilterParams(uId: String, mealFilterParams: MealFilterParams): Single<List<Meal>> {
        return Single.create<List<Meal>> { emitter ->
            firestore.collection(USERS).get()
                .addOnSuccessListener { userDocumentsQuerySnapshot ->
                    userDocumentsQuerySnapshot.forEach { queryDocumentSnapshot ->
                        queryDocumentSnapshot.reference.collection(MEALS).get()
                            .addOnSuccessListener { mealQuerySnapshot ->
                                val list: List<Meal> = mealQuerySnapshot.filter { documentSnapshot ->
                                    val mealFirebase = documentSnapshot.toObject(MealFirebase::class.java)

                                    //TODO map mealTime params
                                    val firebaseMealFilterParams = MealFilterParams(
                                        MealDateParams(
                                            year = mealFirebase.year,
                                            month = mealFirebase.month,
                                            dayOfMonth = mealFirebase.day),
                                        LunchTime())

                                    firebaseMealFilterParams == mealFilterParams
                                }.map { mealDocumentsSnapshot ->
                                    val mealFirebase = mealDocumentsSnapshot.toObject(MealFirebase::class.java)

                                    val mealParams = MealParams(
                                        text = mealFirebase.text,
                                        calories = mealFirebase.calories,
                                        weight = mealFirebase.weight)

                                    val filterParams = MealFilterParams(
                                        MealDateParams(
                                            year = mealFirebase.year,
                                            month = mealFirebase.month,
                                            dayOfMonth = mealFirebase.day),
                                        LunchTime())

                                    val meal = Meal(
                                        id = mealDocumentsSnapshot.id,
                                        params = mealParams,
                                        filterParams = filterParams)

                                    meal
                                }.toList()

                                emitter.onSuccess(list)
                            }
                            .addOnFailureListener { emitter.onError(MealException(error = it)) }
                    }
                }
                .addOnFailureListener { emitter.onError(MealException(error = it)) }
        }
    }
}