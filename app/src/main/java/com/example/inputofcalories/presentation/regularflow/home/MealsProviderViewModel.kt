package com.example.inputofcalories.presentation.regularflow.home

import androidx.lifecycle.MutableLiveData
import com.example.inputofcalories.common.rx.HandleError
import com.example.inputofcalories.common.rx.Success
import com.example.inputofcalories.domain.regularflow.GetMealsFilteredUseCase
import com.example.inputofcalories.domain.regularflow.GetMealsUseCase
import com.example.inputofcalories.entity.presentation.regular.Meal
import com.example.inputofcalories.entity.presentation.Message
import com.example.inputofcalories.entity.presentation.regular.MealFilterParams
import com.example.inputofcalories.presentation.viewModel.BaseViewModel

private const val GET_MEALS_REQUEST_CODE = 1
private const val GET_MEALS_FILTERED_REQUEST_CODE = 2

class MealsProviderViewModel(
    private val userId: String,
    private val getMealsUseCase: GetMealsUseCase,
    private val getMealsFilteredUseCase: GetMealsFilteredUseCase
): BaseViewModel(), HandleError {

    val mealsLoadFailLiveData = MutableLiveData<Any>()

    val mealsLoadSuccessLiveData = MutableLiveData<List<Meal>>()

    val noMealsFoundLiveData = MutableLiveData<Any>()

    fun getMeals() {
        loadMeals(userId) { meals ->
            if (meals.isNotEmpty()) mealsLoadSuccessLiveData.value = meals
            else noMealsFoundLiveData.value = Any()
        }
    }

    fun getMealsFiltered(mealFilterParams: MealFilterParams) {
        loadMealsByFilter(userId, mealFilterParams) { meals ->
            if (meals.isNotEmpty()) mealsLoadSuccessLiveData.value = meals
            else noMealsFoundLiveData.value = Any()
        }
    }

    private fun loadMeals(uId: String, success: Success<List<Meal>>) {
        execute(getMealsUseCase.get(uId),
            requestCode = GET_MEALS_REQUEST_CODE,
            handleError = this,
            success = success)
    }

    private fun loadMealsByFilter(uId: String, mealFilterParams: MealFilterParams, success: Success<List<Meal>>) {
        execute(getMealsFilteredUseCase.get(uId, mealFilterParams),
            requestCode = GET_MEALS_FILTERED_REQUEST_CODE,
            handleError = this,
            success = success)
    }

    override fun invoke(t: Throwable, requestCode: Int?) {
        when(requestCode) {
            GET_MEALS_REQUEST_CODE -> { mealsLoadFailLiveData.value = Any() }
        }
    }

}