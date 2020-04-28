package com.example.inputofcalories.repo.regularflow.model

import com.example.inputofcalories.common.extensions.empty

data class MealFirebase(
    val calories: String,
    val text: String,
    val weight: String,
    val day: String,
    val month: String,
    val year: String,
    val from: String,
    val to: String) {

    @Suppress("UNUSED")
    constructor():
            this(String.empty(),
                String.empty(),
                String.empty(),
                String.empty(),
                String.empty(),
                String.empty(),
                String.empty(),
                String.empty())
}