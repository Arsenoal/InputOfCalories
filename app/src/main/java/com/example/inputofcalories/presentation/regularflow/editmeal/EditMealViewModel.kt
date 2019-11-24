package com.example.inputofcalories.presentation.regularflow.editmeal

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.inputofcalories.common.exception.MealException
import com.example.inputofcalories.domain.regularflow.EditMealUseCase
import com.example.inputofcalories.entity.presentation.Message
import com.example.inputofcalories.entity.presentation.regular.Meal
import com.example.inputofcalories.presentation.base.BaseViewModel
import kotlinx.coroutines.launch

class EditMealViewModel(
    private val editMealUseCase: EditMealUseCase
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

    private suspend fun editMeal(meal: Meal) {
        editMealUseCase.edit(meal)
    }
}