package com.example.inputofcalories.presentation.regularflow.home.viewmodel

import androidx.lifecycle.liveData
import com.example.inputofcalories.common.exception.MealException
import com.example.inputofcalories.domain.regularflow.dailycalories.DailyCaloriesUseCase
import com.example.inputofcalories.entity.presentation.regular.Meal
import com.example.inputofcalories.presentation.base.BaseViewModel
import com.example.inputofcalories.presentation.common.extensions.switchToUi
import com.example.inputofcalories.presentation.regularflow.model.entity.DailyCaloriesLimitState
import com.example.inputofcalories.presentation.regularflow.model.entity.DailyCaloriesState

class DailyCaloriesViewModel(
    private val dailyCaloriesUseCase: DailyCaloriesUseCase
): BaseViewModel() {

    fun saveDailyCaloriesLimit(dailyCalories: String) = liveData {
        try {
            dailyCaloriesUseCase.updateDailyCaloriesLimit(dailyCalories)

            switchToUi { emit(DailyCaloriesState.DailyCaloriesSaveSucceed) }
        } catch (ex: MealException) {
            switchToUi { emit(DailyCaloriesState.DailyCaloriesSaveFailed) }
        }
    }

    fun checkDailyLimit(meals: List<Meal>) = liveData {
        val isLimitExceeded = dailyCaloriesUseCase.isLimitExceeded(meals)
        if(isLimitExceeded)
            switchToUi { emit(DailyCaloriesLimitState.DailyLimitExceeded(meals)) }
        else
            switchToUi { emit(DailyCaloriesLimitState.DailyLimitNotExceeded) }
    }
}