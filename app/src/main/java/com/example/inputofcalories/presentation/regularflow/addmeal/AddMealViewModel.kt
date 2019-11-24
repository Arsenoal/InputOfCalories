package com.example.inputofcalories.presentation.regularflow.addmeal

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.inputofcalories.common.exception.MealException
import com.example.inputofcalories.common.exception.ValidationException
import com.example.inputofcalories.domain.regularflow.AddMealUseCase
import com.example.inputofcalories.domain.regularflow.validation.MealParamsValidationUseCase
import com.example.inputofcalories.entity.presentation.regular.MealFilterParams
import com.example.inputofcalories.entity.presentation.regular.MealParams
import com.example.inputofcalories.presentation.base.BaseViewModel
import kotlinx.coroutines.launch

class AddMealViewModel(
    private val addMealUseCase: AddMealUseCase,
    private val mealParamsValidationUseCase: MealParamsValidationUseCase
): BaseViewModel() {

    val addMealSuccessLiveData = MutableLiveData<Any>()

    val addMealFailLiveData = MutableLiveData<Any>()

    val mealParamsAreInvalidLiveData = MutableLiveData<Any>()

    fun addMealClicked(params: MealParams, filterParams: MealFilterParams) {
        viewModelScope.launch {
            try {
                checkMealParamsValid(params)
                addMeal(params, filterParams)
                addMealSuccessLiveData.value = Any()
            } catch (ex: ValidationException) {
                mealParamsAreInvalidLiveData.value = Any()
            } catch (ex: MealException) {
                addMealFailLiveData.value = Any()
            }
        }
    }

    private suspend fun addMeal(params: MealParams, filterParams: MealFilterParams) {
        addMealUseCase.add(params, filterParams)
    }

    private suspend fun checkMealParamsValid(params: MealParams) {
        mealParamsValidationUseCase.validate(params)
    }
}