package com.example.inputofcalories.presentation.regularflow.editmeal

import androidx.lifecycle.liveData
import com.example.inputofcalories.common.exception.MealException
import com.example.inputofcalories.domain.regularflow.UserMealsUseCase
import com.example.inputofcalories.entity.presentation.regular.Meal
import com.example.inputofcalories.presentation.base.BaseViewModel
import com.example.inputofcalories.presentation.common.extensions.switchToDefault
import com.example.inputofcalories.presentation.common.extensions.switchToUi
import com.example.inputofcalories.presentation.regularflow.model.entity.EditMealState
import kotlinx.coroutines.Dispatchers

class EditMealViewModel(
    private val userMealsUseCase: UserMealsUseCase
): BaseViewModel() {

    fun editClicked(meal: Meal) = liveData(Dispatchers.Main) {
        switchToDefault {
            try {
                userMealsUseCase.editMeal(meal)
                switchToUi { emit(EditMealState.EditMealSucceed) }
            } catch (ex: MealException) { switchToUi { emit(EditMealState.EditMealFailed) } }
        }
    }
}