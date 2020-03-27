package com.example.inputofcalories.presentation.regularflow.editmeal

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.inputofcalories.common.exception.MealException
import com.example.inputofcalories.domain.regularflow.UserMealsUseCase
import com.example.inputofcalories.entity.presentation.Message
import com.example.inputofcalories.entity.presentation.regular.Meal
import com.example.inputofcalories.presentation.base.BaseViewModel
import kotlinx.coroutines.launch

class EditMealViewModel(
    private val userMealsUseCase: UserMealsUseCase
): BaseViewModel() {

    val mealEditSucceededLiveData = MutableLiveData<Any>()

    val mealEditFailedLiveData = MutableLiveData<Message>()

    fun editClicked(meal: Meal) {
        viewModelScope.launch {
            try {
                editMeal(meal)
                mealEditSucceededLiveData.value = Any()
            } catch (ex: MealException) {
                mealEditFailedLiveData.value = Message("meal edit fail")
            }
        }
    }

    private suspend fun editMeal(meal: Meal) = userMealsUseCase.editMeal(meal)
}