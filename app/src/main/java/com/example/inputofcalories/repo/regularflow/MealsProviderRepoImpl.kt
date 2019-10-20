package com.example.inputofcalories.repo.regularflow

import com.example.inputofcalories.common.exception.MealException
import com.example.inputofcalories.entity.Meal
import com.example.inputofcalories.entity.MealParams
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
                                        weight = mealFirebase.weight
                                    )

                                    val meal = Meal(
                                        id = mealDocumentsSnapshot.id,
                                        params = mealParams
                                    )

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