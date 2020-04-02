package com.example.inputofcalories.presentation.regularflow.home.model

import com.example.inputofcalories.presentation.regularflow.model.MealSerializable

data class MealAdapterModel(
    val id: String,
    val text: String,
    val calories: String,
    val weight: String,
    val year: String,
    val month: String,
    val dayOfMonth: String,
    val from: String,
    val to: String,
    var isLimitExceeded: Boolean
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
        from = from,
        to = to
    )
}