package com.example.inputofcalories.presentation.regularflow.addmeal

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.inputofcalories.common.exception.MealException
import com.example.inputofcalories.domain.regularflow.UserMealsUseCase
import com.example.inputofcalories.domain.regularflow.validation.MealParamsValidationUseCase
import com.example.inputofcalories.entity.presentation.regular.MealFilterParams
import com.example.inputofcalories.entity.presentation.regular.MealParams
import com.example.inputofcalories.presentation.base.BaseViewModel
import kotlinx.coroutines.launch

class AddMealViewModel(
    private val userMealsUseCase: UserMealsUseCase,
    private val mealParamsValidationUseCase: MealParamsValidationUseCase
): BaseViewModel() {

    val addMealSuccessLiveData = MutableLiveData<Any>()

    val addMealFailLiveData = MutableLiveData<Any>()

    fun addMealClicked(params: MealParams, filterParams: MealFilterParams) {
        viewModelScope.launch {
            try {
                if(areMealParamsValid(params)) {
                    addMeal(params, filterParams)
                    addMealSuccessLiveData.value = Any()
                }
            } catch (ex: MealException) {
                addMealFailLiveData.value = Any()
            }
        }
    }

    private suspend fun addMeal(params: MealParams, filterParams: MealFilterParams) = userMealsUseCase.addMeal(params, filterParams)

    private suspend fun areMealParamsValid(params: MealParams) = mealParamsValidationUseCase.isValid(params)
}