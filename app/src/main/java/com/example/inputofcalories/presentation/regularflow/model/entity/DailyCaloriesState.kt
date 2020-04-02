package com.example.inputofcalories.presentation.regularflow.model.entity

sealed class DailyCaloriesState {
    object DailyCaloriesSaveSucceed : DailyCaloriesState()

    object DailyCaloriesSaveFailed : DailyCaloriesState()
}

sealed class DailyCaloriesLimitState {
    object DailyLimitExceeded : DailyCaloriesLimitState()

    object DailyLimitNotExceeded : DailyCaloriesLimitState()
}

