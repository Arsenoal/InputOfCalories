package com.example.inputofcalories.presentation.regularflow.home

import androidx.lifecycle.MutableLiveData
import com.example.inputofcalories.common.rx.HandleError
import com.example.inputofcalories.common.rx.Success
import com.example.inputofcalories.common.rx.SuccessCompletable
import com.example.inputofcalories.domain.regularflow.UpdateUsersDailyCaloriesUseCase
import com.example.inputofcalories.domain.user.GetUserUseCase
import com.example.inputofcalories.entity.presentation.Message
import com.example.inputofcalories.entity.register.User
import com.example.inputofcalories.presentation.viewModel.BaseViewModel

private const val GET_USER_REQUEST_CODE = 1
private const val UPDATE_DAILY_CALORIES_REQUEST_CODE = 2

class UpdateDailyCaloriesViewModel(
    private val getUserUseCase: GetUserUseCase,
    private val updateUsersDailyCaloriesUseCase: UpdateUsersDailyCaloriesUseCase
): BaseViewModel(), HandleError {

    val updateCaloriesSucceedLiveData = MutableLiveData<Message>()

    val updateCaloriesFailedLiveData = MutableLiveData<Message>()

    fun applyClicked(dailyCalories: String) {
        getUser { user ->
            update(user.id, dailyCalories) {
                updateCaloriesSucceedLiveData.value = Message("update succeed")
            }
        }
    }

    private fun getUser(success: Success<User>) {
        execute(getUserUseCase.get(),
            requestCode = GET_USER_REQUEST_CODE,
            handleError = this,
            success = success)
    }

    private fun update(userId: String, dailyCalories: String, success: SuccessCompletable) {
        execute(updateUsersDailyCaloriesUseCase.update(userId, dailyCalories),
            requestCode = UPDATE_DAILY_CALORIES_REQUEST_CODE,
            handleError = this,
            success = success)
    }

    override fun invoke(t: Throwable, requestCode: Int?) {
        when(requestCode) {
            GET_USER_REQUEST_CODE -> {}
            UPDATE_DAILY_CALORIES_REQUEST_CODE -> {
                updateCaloriesFailedLiveData.value = Message("update failed")
            }
        }
    }
}