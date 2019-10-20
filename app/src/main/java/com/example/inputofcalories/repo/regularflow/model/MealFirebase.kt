package com.example.inputofcalories.repo.regularflow.model

class MealFirebase(
    val calories: String,
    val text: String,
    val weight: String
) {
    constructor(): this("", "", "")
}