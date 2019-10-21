package com.example.inputofcalories.presentation.regularflow

import androidx.lifecycle.MutableLiveData
import com.example.inputofcalories.common.extensions.empty
import com.example.inputofcalories.common.rx.HandleError
import com.example.inputofcalories.common.rx.SuccessCompletable
import com.example.inputofcalories.domain.regularflow.AddMealUseCase
import com.example.inputofcalories.entity.Meal
import com.example.inputofcalories.entity.MealParams
import com.example.inputofcalories.entity.presentation.Message
import com.example.inputofcalories.presentation.viewModel.BaseViewModel

const val ADD_MEAL_REQUEST_CODE = 1

class AddMealViewModel(
    private val addMealUseCase: AddMealUseCase
): BaseViewModel(), HandleError {

    val addMealSuccessLiveData = MutableLiveData<Any>()

    val addMealFailLiveData = MutableLiveData<Message>()

    fun addMealClicked(mealParams: MealParams) {
        addMeal(mealParams) {
            addMealSuccessLiveData.value = Any()
        }
    }

    private fun addMeal(mealParams: MealParams, success: SuccessCompletable) {
        execute(addMealUseCase.add(mealParams),
            requestCode = ADD_MEAL_REQUEST_CODE,
            handleError = this,
            success = success)
    }

    override fun invoke(t: Throwable, requestCode: Int?) {
        when(requestCode) {
            ADD_MEAL_REQUEST_CODE -> {
                addMealFailLiveData.value = Message(t.message ?: String.empty())
            }
        }
    }
}