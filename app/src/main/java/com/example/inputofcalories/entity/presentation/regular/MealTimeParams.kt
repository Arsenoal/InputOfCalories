package com.example.inputofcalories.entity.presentation.regular

import java.io.Serializable

abstract class MealTimeParams(val from: String, val to: String): Serializable

object BreakfastTime: MealTimeParams("9", "12")

object LunchTime: MealTimeParams("12", "15")

object DinnerTime: MealTimeParams("15", "18")

object SnackTime: MealTimeParams("18", "21")

