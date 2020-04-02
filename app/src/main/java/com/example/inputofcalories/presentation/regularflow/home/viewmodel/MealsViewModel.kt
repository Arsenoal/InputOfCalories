package com.example.inputofcalories.presentation.regularflow.home.viewmodel

import androidx.lifecycle.liveData
import com.example.inputofcalories.common.exception.MealException
import com.example.inputofcalories.domain.regularflow.UserMealsUseCase
import com.example.inputofcalories.domain.regularflow.validation.MealParamsValidationUseCase
import com.example.inputofcalories.domain.user.UserUseCase
import com.example.inputofcalories.entity.presentation.regular.MealDeleteParams
import com.example.inputofcalories.entity.presentation.regular.MealFilterParams
import com.example.inputofcalories.entity.presentation.regular.MealParams
import com.example.inputofcalories.entity.presentation.regular.Meal
import com.example.inputofcalories.presentation.base.BaseViewModel
import com.example.inputofcalories.presentation.common.extensions.switchToDefault
import com.example.inputofcalories.presentation.common.extensions.switchToUi
import com.example.inputofcalories.presentation.regularflow.model.entity.DeleteMealState.*
import com.example.inputofcalories.presentation.regularflow.model.entity.AddMealState.*
import com.example.inputofcalories.presentation.regularflow.model.entity.EditMealState.*
import com.example.inputofcalories.presentation.regularflow.model.entity.GetMealsFilteredState.*
import com.example.inputofcalories.presentation.regularflow.model.entity.GetMealsState.*
import kotlinx.coroutines.Dispatchers

class MealsViewModel(
    private val userUseCase: UserUseCase,
    private val userMealsUseCase: UserMealsUseCase,
    private val mealParamsValidationUseCase: MealParamsValidationUseCase
): BaseViewModel() {

    fun getMeals() = liveData(Dispatchers.Main) {
        switchToDefault {
            try {
                val userId = userUseCase.get().id
                val meals = userMealsUseCase.getMeals(userId)

                switchToUi { emit(GetMealsSucceed(meals)) }

            } catch (ex: MealException) { switchToUi { emit(GetMealsFailed) } }
        }
    }

    fun getMealsFiltered(mealFilterParams: MealFilterParams) = liveData(Dispatchers.Main) {
        switchToDefault {
            try {
                val userId = userUseCase.get().id
                val meals = userMealsUseCase.getMealsFiltered(userId, mealFilterParams)

                switchToUi { emit(GetMealsFilteredSucceed(meals)) }

            } catch (ex: MealException) { switchToUi { emit(GetMealsFilteredFailed) } }
        }
    }

    fun deleteMeal(mealId: String) = liveData(Dispatchers.Main) {
        try {
            val userId = userUseCase.get().id
            userMealsUseCase.deleteMeal(MealDeleteParams(userId, mealId))

            switchToUi { emit(DeleteMealSucceed) }

        } catch (ex: MealException) { switchToUi { emit(DeleteMealFailed) } }
    }

    fun addMeal(params: MealParams, filterParams: MealFilterParams) = liveData(Dispatchers.Main) {
        val areMealParamsValid = mealParamsValidationUseCase.isValid(params)

        if(areMealParamsValid) {
            try {
                userMealsUseCase.addMeal(params, filterParams)
                switchToUi { emit(AddMealSucceed) }

            } catch (ex: MealException) { switchToUi { emit(AddMealFailed) } }
        }
    }

    fun editMeal(meal: Meal) = liveData(Dispatchers.Main) {
        try {
            userMealsUseCase.editMeal(meal)
            switchToUi { emit(EditMealSucceed) }

        } catch (ex: MealException) { switchToUi { emit(EditMealFailed) } }
    }
}