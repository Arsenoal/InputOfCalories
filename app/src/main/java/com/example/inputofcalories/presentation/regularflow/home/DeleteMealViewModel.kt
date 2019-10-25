package com.example.inputofcalories.presentation.regularflow.home

import androidx.lifecycle.MutableLiveData
import com.example.inputofcalories.common.rx.HandleError
import com.example.inputofcalories.common.rx.Success
import com.example.inputofcalories.common.rx.SuccessCompletable
import com.example.inputofcalories.domain.regularflow.DeleteMealUseCase
import com.example.inputofcalories.domain.user.GetUserUseCase
import com.example.inputofcalories.entity.presentation.Message
import com.example.inputofcalories.entity.presentation.regular.MealDeleteParams
import com.example.inputofcalories.entity.register.User
import com.example.inputofcalories.presentation.viewModel.BaseViewModel

private const val GET_USER_REQUEST_CODE = 1
private const val DELETE_MEAL_REQUEST_CODE = 2

class DeleteMealViewModel(
    private val getUserUseCase: GetUserUseCase,
    private val deleteMealUseCase: DeleteMealUseCase
): BaseViewModel(), HandleError {

    val deleteMealFailLiveData = MutableLiveData<Message>()

    val deleteMealSuccessLiveData = MutableLiveData<Any>()

    fun deleteMealClicked(mealId: String) {
        getUser { user ->
            val mealDeleteParams = MealDeleteParams(user.id, mealId)
            deleteMeal(mealDeleteParams) {
                deleteMealSuccessLiveData.value = Any()
            }
        }
    }

    private fun getUser(success: Success<User>) {
        execute(getUserUseCase.get(),
            requestCode = GET_USER_REQUEST_CODE,
            handleError = this,
            success = success)
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