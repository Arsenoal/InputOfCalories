package com.example.inputofcalories.presentation.regularflow.addmeal

import androidx.lifecycle.liveData
import com.example.inputofcalories.common.exception.MealException
import com.example.inputofcalories.domain.regularflow.UserMealsUseCase
import com.example.inputofcalories.domain.regularflow.validation.MealParamsValidationUseCase
import com.example.inputofcalories.entity.presentation.regular.MealFilterParams
import com.example.inputofcalories.entity.presentation.regular.MealParams
import com.example.inputofcalories.presentation.base.BaseViewModel
import com.example.inputofcalories.presentation.common.extensions.switchToDefault
import com.example.inputofcalories.presentation.common.extensions.switchToUi
import com.example.inputofcalories.presentation.regularflow.model.entity.AddMealState
import kotlinx.coroutines.Dispatchers

class AddMealViewModel(
    private val userMealsUseCase: UserMealsUseCase,
    private val mealParamsValidationUseCase: MealParamsValidationUseCase
): BaseViewModel() {

    fun addMeal(params: MealParams, filterParams: MealFilterParams) = liveData(Dispatchers.Main) {
        switchToDefault {
            try {
                if(mealParamsValidationUseCase.isValid(params)) {
                    userMealsUseCase.addMeal(params, filterParams)
                    switchToUi { emit(AddMealState.AddMealSucceed) }
                }
            } catch (ex: MealException) {
                switchToUi { emit(AddMealState.AddMealFailed) }
            }
        }
    }
}