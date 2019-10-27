package com.example.inputofcalories.presentation.regularflow.home

import androidx.lifecycle.MutableLiveData
import com.example.inputofcalories.common.rx.HandleError
import com.example.inputofcalories.common.rx.SuccessCompletable
import com.example.inputofcalories.domain.regularflow.DeleteMealUseCase
import com.example.inputofcalories.entity.presentation.Message
import com.example.inputofcalories.entity.presentation.regular.MealDeleteParams
import com.example.inputofcalories.presentation.viewModel.BaseViewModel

private const val GET_USER_REQUEST_CODE = 1
private const val DELETE_MEAL_REQUEST_CODE = 2

class DeleteMealViewModel(
    private val userId: String,
    private val deleteMealUseCase: DeleteMealUseCase
): BaseViewModel(), HandleError {

    val deleteMealFailLiveData = MutableLiveData<Message>()

    val deleteMealSuccessLiveData = MutableLiveData<Any>()

    fun deleteMealClicked(mealId: String) {
        val mealDeleteParams = MealDeleteParams(userId, mealId)
        deleteMeal(mealDeleteParams) {
            deleteMealSuccessLiveData.value = Any()
        }
    }

    private fun deleteMeal(mealDeleteParams: MealDeleteParams, success: SuccessCompletable) {
        execute(deleteMealUseCase.delete(mealDeleteParams),
            requestCode = DELETE_MEAL_REQUEST_CODE,
            handleError = this,
            success = success)
    }

    override fun invoke(t: Throwable, requestCode: Int?) {
        when(requestCode) {
            GET_USER_REQUEST_CODE -> {}
            DELETE_MEAL_REQUEST_CODE -> {
                deleteMealFailLiveData.value = Message("meal delete fail")
            }
        }
    }
}