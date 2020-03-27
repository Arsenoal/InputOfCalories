package com.example.inputofcalories.presentation.regularflow.home.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.inputofcalories.common.exception.MealException
import com.example.inputofcalories.domain.regularflow.UserMealsUseCase
import com.example.inputofcalories.entity.presentation.regular.Meal
import com.example.inputofcalories.entity.presentation.regular.MealFilterParams
import com.example.inputofcalories.presentation.base.BaseViewModel
import kotlinx.coroutines.launch

class MealsProviderViewModel(
    private val userId: String,
    private val userMealsUseCase: UserMealsUseCase
): BaseViewModel() {

    val mealsLoadFailLiveData = MutableLiveData<Any>()

    val mealsLoadSuccessLiveData = MutableLiveData<List<Meal>>()

    val noMealsFoundLiveData = MutableLiveData<Any>()

    fun getMeals() {
        viewModelScope.launch {
            try {
                val meals = loadMeals(userId)

                if (meals.isNotEmpty()) mealsLoadSuccessLiveData.value = meals
                else noMealsFoundLiveData.value = Any()
            } catch (ex: MealException) {
                mealsLoadFailLiveData.value = Any()
            }
        }
    }

    fun getMealsFiltered(mealFilterParams: MealFilterParams) {
        viewModelScope.launch {
            try {
                val meals = loadMealsByFilter(userId, mealFilterParams)

                if (meals.isNotEmpty()) mealsLoadSuccessLiveData.value = meals
                else noMealsFoundLiveData.value = Any()
            } catch (ex: MealException) {
                mealsLoadFailLiveData.value = Any()
            }
        }
    }

    private suspend fun loadMeals(uId: String) = userMealsUseCase.getMeals(uId)

    private suspend fun loadMealsByFilter(uId: String, mealFilterParams: MealFilterParams) = userMealsUseCase.getMealsFiltered(uId, mealFilterParams)

}