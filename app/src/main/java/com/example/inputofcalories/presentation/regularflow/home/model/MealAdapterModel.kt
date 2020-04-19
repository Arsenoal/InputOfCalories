package com.example.inputofcalories.presentation.regularflow.home.model

import com.example.inputofcalories.entity.presentation.regular.MealTimeParams
import com.example.inputofcalories.presentation.regularflow.model.MealSerializable

data class MealAdapterModel(
    val id: String,
    val text: String,
    val calories: String,
    val weight: String,
    val year: String,
    val month: String,
    val dayOfMonth: String,
    val timeParams: MealTimeParams,
    var isLimitExceeded: Boolean
)

data class DeleteParams(
    val mealId: String,
    val position: Int
)

fun MealAdapterModel.toMealSerializable() = with(this) {
    MealSerializable(
        id = id,
        text = text,
        calories = calories,
        weight = weight,
        year = year,
        month = month,
        dayOfMonth = dayOfMonth,
        from = timeParams.from,
        to = timeParams.to
    )
}