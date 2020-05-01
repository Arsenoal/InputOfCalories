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
    val year: String,
    val month: String,
    val dayOfMount: String,
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
        timeParam = timeParams
    )
}

fun MealAdapterModel.toDeleteParams(position: Int) = with(this) {
    DeleteParams(
        mealId = id,
        year = year,
        month = month,
        dayOfMount = dayOfMonth,
        position = position
    )
}

fun List<MealAdapterModel>.containsId(id: String): Boolean{
    return find { it.id == id } != null
}