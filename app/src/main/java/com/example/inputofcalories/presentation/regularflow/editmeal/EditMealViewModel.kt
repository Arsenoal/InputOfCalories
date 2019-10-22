package com.example.inputofcalories.presentation.regularflow.editmeal

import androidx.lifecycle.MutableLiveData
import com.example.inputofcalories.common.rx.HandleError
import com.example.inputofcalories.common.rx.SuccessCompletable
import com.example.inputofcalories.domain.regularflow.EditMealUseCase
import com.example.inputofcalories.entity.MealParams
import com.example.inputofcalories.entity.presentation.Message
import com.example.inputofcalories.presentation.regularflow.model.MealSerializable
import com.example.inputofcalories.presentation.viewModel.BaseViewModel

const val EDIT_MEAL_REQUEST_CODE = 1

class EditMealViewModel(
    private val editMealUseCase: EditMealUseCase
): BaseViewModel(), HandleError {

    val mealEditSucceededLiveData = MutableLiveData<Any>()

    val mealEditFailedLiveData = MutableLiveData<Message>()

    fun editClicked(mealSerializable: MealSerializable) {
        val params = MealParams(
            text = mealSerializable.text,
            calories = mealSerializable.calories,
            weight = mealSerializable.weight
        )

        editMeal(mealSerializable.id, params) {
            mealEditSucceededLiveData.value = Any()
        }
    }

    private fun editMeal(mealId: String, params: MealParams, success: SuccessCompletable) {
        execute(editMealUseCase.edit(mealId, params),
            requestCode = EDIT_MEAL_REQUEST_CODE,
            handleError = this,
            success = success)
    }

    override fun invoke(t: Throwable, requestCode: Int?) {
        when(requestCode) {
            EDIT_MEAL_REQUEST_CODE -> {
                mealEditFailedLiveData.value = Message("meal edit fail")
            }
        }
    }
}