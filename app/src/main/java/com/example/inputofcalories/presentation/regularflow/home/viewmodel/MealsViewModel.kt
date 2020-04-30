package com.example.inputofcalories.presentation.regularflow.home.viewmodel

import androidx.lifecycle.liveData
import com.example.inputofcalories.common.exception.MealException
import com.example.inputofcalories.domain.regularflow.UserMealsUseCase
import com.example.inputofcalories.domain.user.UserUseCase
import com.example.inputofcalories.entity.presentation.regular.MealDeleteParams
import com.example.inputofcalories.entity.presentation.regular.MealFilterParams
import com.example.inputofcalories.presentation.base.BaseViewModel
import com.example.inputofcalories.presentation.common.extensions.switchToDefault
import com.example.inputofcalories.presentation.common.extensions.switchToUi
import com.example.inputofcalories.presentation.regularflow.home.model.DeleteParams
import com.example.inputofcalories.presentation.regularflow.model.entity.DeleteMealState.*
import com.example.inputofcalories.presentation.regularflow.model.entity.GetMealsFilteredState.*
import com.example.inputofcalories.presentation.regularflow.model.entity.GetMealsState.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import java.util.*

class MealsViewModel(
    private val userUseCase: UserUseCase,
    private val userMealsUseCase: UserMealsUseCase
): BaseViewModel() {

    fun loadMeals() = liveData(Dispatchers.Main) {
        switchToDefault {
            try {
                val userId = userUseCase.get().id
                val meals = userMealsUseCase.loadMeals(userId)

                switchToUi {
                    if(meals.isEmpty()) emit(NoMealsToShow)
                    else emit(GetMealsSucceed(meals))
                }

            } catch (ex: MealException) { switchToUi { emit(GetMealsFailed) } }
        }
    }

    fun loadMoreMeals(page: Int) = liveData(Dispatchers.Main) {
        switchToDefault {
            try {
                val date = with(Calendar.getInstance()) {
                    time = Date()
                    add(Calendar.DATE, 0 - page)

                    this
                }.time

                val userId = userUseCase.get().id
                val meals = userMealsUseCase.loadMoreMeals(userId, date)

                switchToUi { emit(GetMealsSucceed(meals)) }
            } catch (ex: MealException) { switchToUi { emit(GetMealsFailed) } }
        }
    }

    fun getMealsFiltered(mealFilterParams: List<MealFilterParams>) = liveData(Dispatchers.Main) {
        switchToDefault {
            try {
                val userId = userUseCase.get().id
                val meals =
                    if(mealFilterParams.isNotEmpty()) userMealsUseCase.getMealsFiltered(userId, mealFilterParams)
                    else userMealsUseCase.loadMeals(userId)

                switchToUi { emit(GetMealsFilteredSucceed(meals)) }

            } catch (ex: MealException) { switchToUi { emit(GetMealsFilteredFailed) } }
        }
    }

    fun deleteMeal(deleteParams: DeleteParams) = liveData(Dispatchers.Main) {
        try {
            delay(150)
            val userId = userUseCase.get().id
            userMealsUseCase.deleteMeal(MealDeleteParams(userId, deleteParams.mealId))

            switchToUi { emit(DeleteMealSucceed(deleteParams.position)) }

        } catch (ex: MealException) { switchToUi { emit(DeleteMealFailed) } }
    }
}