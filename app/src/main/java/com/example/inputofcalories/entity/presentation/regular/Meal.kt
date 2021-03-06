package com.example.inputofcalories.entity.presentation.regular

import com.example.inputofcalories.presentation.regularflow.home.model.MealAdapterModel
import com.example.inputofcalories.presentation.regularflow.model.MealSerializable
import com.example.inputofcalories.repo.regularflow.model.MealFirebase

data class Meal(val id: String, val params: MealParams, val filterParams: MealFilterParams) {
    override fun toString() = "Meal: {id: $id, params: $params}"
}

data class MealParams(val text: String, val calories: String, val weight: String) {
    override fun toString() = "Meal params: {text: $text, calories: $calories, weight: $weight}"
}

data class MealFilterParams(var date: MealDateParams, var time: MealTimeParams) {
    override fun toString() = "MealFilterParams(date=$date, time=from: ${time.from}, to: ${time.to})"

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as MealFilterParams

        if (date != other.date) return false
        if (time.from != other.time.from || time.to != other.time.to) return false

        return true
    }

    override fun hashCode(): Int {
        var result = date.hashCode()
        result = 31 * result + time.from.hashCode()
        result = 31 * result + time.to.hashCode()
        return result
    }
}

fun Meal.toAdapterModel() = with(this) {
    MealAdapterModel(
        id = id,
        text = params.text,
        calories = params.calories,
        weight = params.weight,
        dayOfMonth = filterParams.date.dayOfMonth,
        month = filterParams.date.month,
        year = filterParams.date.year,
        timeParams = filterParams.time,
        isLimitExceeded = false
    )
}

fun Meal.toMealSerializable() = with(this) {
    MealSerializable(
        id = id,
        text = params.text,
        calories = params.calories,
        weight = params.weight,
        dayOfMonth = filterParams.date.dayOfMonth,
        month = filterParams.date.month,
        year = filterParams.date.year,
        timeParam = filterParams.time
    )
}

fun Meal.toMealFirebase() = with(this) {
    MealFirebase(
        calories = params.calories,
        text = params.text,
        weight = params.weight,
        day = filterParams.date.dayOfMonth,
        month = filterParams.date.month,
        year = filterParams.date.year,
        from = filterParams.time.from,
        to = filterParams.time.to
    )
}