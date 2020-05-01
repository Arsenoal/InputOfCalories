package com.example.inputofcalories.presentation.regularflow.model.entity

import com.example.inputofcalories.entity.presentation.regular.Meal

sealed class DailyCaloriesState {
    object DailyCaloriesSaveSucceed : DailyCaloriesState()

    object DailyCaloriesSaveFailed : DailyCaloriesState()
}

sealed class DailyCaloriesLimitState {
    class DailyLimitExceeded(val meals: List<Meal>) : DailyCaloriesLimitState()

    object DailyLimitNotExceeded : DailyCaloriesLimitState()
}

