package com.example.inputofcalories.presentation.regularflow.home.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.inputofcalories.common.exception.MealException
import com.example.inputofcalories.domain.regularflow.UserMealsUseCase
import com.example.inputofcalories.entity.presentation.regular.MealDeleteParams
import com.example.inputofcalories.presentation.base.BaseViewModel
import kotlinx.coroutines.launch

class DeleteMealViewModel(
    private val userId: String,
    private val userMealsUseCase: UserMealsUseCase
): BaseViewModel() {

    val deleteMealFailLiveData = MutableLiveData<Any>()

    val deleteMealSuccessLiveData = MutableLiveData<Any>()

    fun deleteMealClicked(mealId: String) {
        viewModelScope.launch {
            try {
                deleteMeal(MealDeleteParams(userId, mealId))
                deleteMealSuccessLiveData.value = Any()
            } catch (ex: MealException) {
                deleteMealFailLiveData.value = Any()
            }
        }
    }

    private suspend fun deleteMeal(mealDeleteParams: MealDeleteParams) = userMealsUseCase.deleteMeal(mealDeleteParams)
}