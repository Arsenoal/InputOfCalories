package com.example.inputofcalories.presentation.regularflow.home

import androidx.lifecycle.MutableLiveData
import com.example.inputofcalories.common.rx.HandleError
import com.example.inputofcalories.common.rx.Success
import com.example.inputofcalories.domain.regularflow.GetMealsFilteredUseCase
import com.example.inputofcalories.domain.regularflow.GetMealsUseCase
import com.example.inputofcalories.domain.user.GetUserUseCase
import com.example.inputofcalories.entity.presentation.regular.Meal
import com.example.inputofcalories.entity.presentation.Message
import com.example.inputofcalories.entity.presentation.regular.MealFilterParams
import com.example.inputofcalories.entity.register.User
import com.example.inputofcalories.presentation.viewModel.BaseViewModel

const val GET_USER_REQUEST_CODE = 1
const val GET_MEALS_REQUEST_CODE = 2
const val GET_MEALS_FILTERED_REQUEST_CODE = 3

class MealsProviderViewModel(
    private val getMealsUseCase: GetMealsUseCase,
    private val getMealsFilteredUseCase: GetMealsFilteredUseCase,
    private val getUserUseCase: GetUserUseCase
): BaseViewModel(), HandleError {

    val mealsLoadFailLiveData = MutableLiveData<Message>()

    val mealsLoadSuccessLiveData = MutableLiveData<List<Meal>>()

    val noMealsFoundLiveData = MutableLiveData<Any>()

    fun getMeals() {
        getUser { user ->
            loadMeals(user.id) { meals ->
                if (meals.isNotEmpty()) mealsLoadSuccessLiveData.value = meals
                else noMealsFoundLiveData.value = Any()
            }
        }

    }

    fun getMealsFiltered(mealFilterParams: MealFilterParams) {
        getUser { user ->
            loadMealsByFilter(user.id, mealFilterParams) { meals ->
                if (meals.isNotEmpty()) mealsLoadSuccessLiveData.value = meals
                else noMealsFoundLiveData.value = Any()
            }
        }
    }

    private fun getUser(success: Success<User>) {
        execute(getUserUseCase.get(),
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

    private fun loadMealsByFilter(uId: String, mealFilterParams: MealFilterParams, success: Success<List<Meal>>) {
        execute(getMealsFilteredUseCase.get(uId, mealFilterParams),
            requestCode = GET_MEALS_FILTERED_REQUEST_CODE,
            handleError = this,
            success = success)
    }

    override fun invoke(t: Throwable, requestCode: Int?) {
        when(requestCode) {
            GET_USER_REQUEST_CODE -> { mealsLoadFailLiveData.value = Message("failed to get user") }
            GET_MEALS_REQUEST_CODE -> { mealsLoadFailLiveData.value = Message("failed to load meals") }
        }
    }
}