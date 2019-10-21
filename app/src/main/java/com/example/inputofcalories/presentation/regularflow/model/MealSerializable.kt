package com.example.inputofcalories.presentation.regularflow.model

import java.io.Serializable

data class MealSerializable(
    val id: String,
    val text: String,
    val calories: String,
    val weight: String
): Serializable