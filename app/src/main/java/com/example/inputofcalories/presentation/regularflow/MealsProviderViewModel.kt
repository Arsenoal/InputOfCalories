package com.example.inputofcalories.presentation.regularflow

import androidx.lifecycle.MutableLiveData
import com.example.inputofcalories.common.logger.IOFLogger
import com.example.inputofcalories.common.rx.HandleError
import com.example.inputofcalories.common.rx.Success
import com.example.inputofcalories.domain.regularflow.GetMealsUseCase
import com.example.inputofcalories.domain.user.GetUserFromLocalUseCase
import com.example.inputofcalories.entity.Meal
import com.example.inputofcalories.entity.presentation.Message
import com.example.inputofcalories.entity.register.User
import com.example.inputofcalories.presentation.viewModel.BaseViewModel

const val GET_USER_REQUEST_CODE = 1
const val GET_MEALS_REQUEST_CODE = 2

class MealsProviderViewModel(
    private val getMealsUseCase: GetMealsUseCase,
    private val getUserFromLocalUseCase: GetUserFromLocalUseCase
): BaseViewModel(), HandleError {

    val mealsLoadFailLiveData = MutableLiveData<Message>()

    val mealsLoadSuccessLiveData = MutableLiveData<List<Meal>>()

    fun onGetMealsClicked() {
        getUser { user ->
            loadMeals(user.id ?: "") { meals ->
                mealsLoadSuccessLiveData.value = meals
            }
        }

    }

    private fun getUser(success: Success<User>) {
        execute(getUserFromLocalUseCase.get(),
            requestCode = GET_USER_REQUEST_CODE,
            handleError = this,
            success = success)
    }

    private fun loadMeals(uId: String, success: Success<List<Meal>>) {
        execute(getMealsUseCase.get(uId),
            requestCode = GET_MEALS_REQUEST_CODE,
            handleError = this,
            success = success)
    }

    override fun invoke(t: Throwable, requestCode: Int?) {
        when(requestCode) {
            GET_MEALS_REQUEST_CODE -> { mealsLoadFailLiveData.value = Message("failed to load meals") }
        }
    }
}