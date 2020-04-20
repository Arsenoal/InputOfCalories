package com.example.inputofcalories.presentation.regularflow.model

import com.example.inputofcalories.entity.presentation.regular.MealTimeParams
import java.io.Serializable

data class MealSerializable(
    val id: String,
    val text: String,
    val calories: String,
    val weight: String,
    val year: String,
    val month: String,
    val dayOfMonth: String,
    val timeParam: MealTimeParams
): Serializable