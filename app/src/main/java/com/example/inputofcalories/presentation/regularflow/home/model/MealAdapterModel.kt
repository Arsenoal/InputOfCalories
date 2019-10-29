package com.example.inputofcalories.presentation.regularflow.home.model

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