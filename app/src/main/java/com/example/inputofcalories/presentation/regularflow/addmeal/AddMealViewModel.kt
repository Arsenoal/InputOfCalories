package com.example.inputofcalories.presentation.regularflow.addmeal

import androidx.lifecycle.MutableLiveData
import com.example.inputofcalories.common.rx.HandleError
import com.example.inputofcalories.common.rx.SuccessCompletable
import com.example.inputofcalories.domain.regularflow.AddMealUseCase
import com.example.inputofcalories.entity.presentation.regular.MealFilterParams
import com.example.inputofcalories.entity.presentation.regular.MealParams
import com.example.inputofcalories.presentation.viewModel.BaseViewModel

const val ADD_MEAL_REQUEST_CODE = 1

class AddMealViewModel(
    private val addMealUseCase: AddMealUseCase
): BaseViewModel(), HandleError {

    val addMealSuccessLiveData = MutableLiveData<Any>()

    val addMealFailLiveData = MutableLiveData<Any>()

    fun addMealClicked(params: MealParams, filterParams: MealFilterParams) {
        addMeal(params, filterParams) {
            addMealSuccessLiveData.value = Any()
        }
    }

    private fun addMeal(params: MealParams, filterParams: MealFilterParams, success: SuccessCompletable) {
        execute(addMealUseCase.add(params, filterParams),
            requestCode = ADD_MEAL_REQUEST_CODE,
            handleError = this,
            success = success)
    }

    override fun invoke(t: Throwable, requestCode: Int?) {
        when(requestCode) {
            ADD_MEAL_REQUEST_CODE -> {
                addMealFailLiveData.value = Any()
            }
        }
    }
}