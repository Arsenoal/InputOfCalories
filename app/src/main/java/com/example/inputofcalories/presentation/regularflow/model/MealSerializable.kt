package com.example.inputofcalories.presentation.regularflow.model

import java.io.Serializable

data class MealSerializable(
    val id: String,
    val text: String,
    val calories: String,
    val weight: String,
    val year: String,
    val month: String,
    val dayOfMonth: String,
    val from: String,
    val to: String
): Serializable