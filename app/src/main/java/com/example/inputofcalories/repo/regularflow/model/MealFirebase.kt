package com.example.inputofcalories.repo.regularflow.model

data class MealFirebase(
    val calories: String,
    val text: String,
    val weight: String,
    val day: String,
    val month: String,
    val year: String,
    val from: String,
    val to: String
) {
    constructor(): this("", "", "", "", "", "", "", "")
}