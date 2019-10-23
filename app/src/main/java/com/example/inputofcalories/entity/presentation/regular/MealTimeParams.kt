package com.example.inputofcalories.entity.presentation.regular

abstract class MealTimeParams(val from: String, val to: String)

class LunchTime: MealTimeParams("12", "15")