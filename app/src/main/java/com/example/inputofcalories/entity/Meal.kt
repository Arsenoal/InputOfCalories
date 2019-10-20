package com.example.inputofcalories.entity

data class Meal(
    val id: String,
    val params: MealParams
) {
    override fun toString(): String {
        return "Meal: {$id params: $params}"
    }
}

data class MealParams(
    val text: String,
    val calories: String,
    val weight: String
) {
    override fun toString(): String {
        return "$text $calories $weight"
    }
}