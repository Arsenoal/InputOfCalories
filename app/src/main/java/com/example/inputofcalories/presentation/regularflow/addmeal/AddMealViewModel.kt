package com.example.inputofcalories.presentation.regularflow.addmeal

import androidx.lifecycle.MutableLiveData
import com.example.inputofcalories.common.rx.HandleError
import com.example.inputofcalories.common.rx.SuccessCompletable
import com.example.inputofcalories.domain.regularflow.AddMealUseCase
import com.example.inputofcalories.domain.regularflow.validation.MealParamsValidationUseCase
import com.example.inputofcalories.entity.presentation.regular.MealFilterParams
import com.example.inputofcalories.entity.presentation.regular.MealParams
import com.example.inputofcalories.presentation.viewModel.BaseViewModel

const val ADD_MEAL_REQUEST_CODE = 1
const val MEAL_PARAMS_VALID_REQUEST_CODE = 2

class AddMealViewModel(
    private val addMealUseCase: AddMealUseCase,
    private val mealParamsValidationUseCase: MealParamsValidationUseCase
): BaseViewModel(), HandleError {

    val addMealSuccessLiveData = MutableLiveData<Any>()

    val addMealFailLiveData = MutableLiveData<Any>()

    val mealParamsAreInvalidLiveData = MutableLiveData<Any>()

    fun addMealClicked(params: MealParams, filterParams: MealFilterParams) {
        checkMealParamsValid(params) {
            addMeal(params, filterParams) {
                addMealSuccessLiveData.value = Any()
            }
        }
    }

    private fun addMeal(params: MealParams, filterParams: MealFilterParams, success: SuccessCompletable) {
        execute(addMealUseCase.add(params, filterParams),
            requestCode = ADD_MEAL_REQUEST_CODE,
            handleError = this,
            success = success)
    }

    private fun checkMealParamsValid(params: MealParams, success: SuccessCompletable) {
        execute(mealParamsValidationUseCase.validate(params),
            requestCode = MEAL_PARAMS_VALID_REQUEST_CODE,
            handleError = this,
            success = success)
    }

    override fun invoke(t: Throwable, requestCode: Int?) {
        when(requestCode) {
            ADD_MEAL_REQUEST_CODE -> { addMealFailLiveData.value = Any() }
            MEAL_PARAMS_VALID_REQUEST_CODE -> { mealParamsAreInvalidLiveData.value = Any() }
        }
    }
}