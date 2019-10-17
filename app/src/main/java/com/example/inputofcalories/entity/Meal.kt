package com.example.inputofcalories.entity

import java.util.*

data class Meal(
    val id: UUID,
    val params: MealParams
)

data class MealParams(
    val name: String,
    val calories: String
)