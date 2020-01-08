package com.example.inputofcalories.presentation.regularflow.home.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.inputofcalories.common.exception.UserDailyCaloriesUpdateException
import com.example.inputofcalories.domain.regularflow.UpdateUsersDailyCaloriesUseCase
import com.example.inputofcalories.entity.presentation.Message
import com.example.inputofcalories.presentation.base.BaseViewModel
import kotlinx.coroutines.launch

class UpdateDailyCaloriesViewModel(
    private val userId: String,
    private val updateUsersDailyCaloriesUseCase: UpdateUsersDailyCaloriesUseCase
): BaseViewModel() {

    val updateCaloriesSucceedLiveData = MutableLiveData<Any>()

    val updateCaloriesFailedLiveData = MutableLiveData<Any>()

    fun applyClicked(dailyCalories: String) {
        viewModelScope.launch {
            try {
                update(userId, dailyCalories)
                updateCaloriesSucceedLiveData.value = Any()
            } catch (ex: UserDailyCaloriesUpdateException) {
                updateCaloriesFailedLiveData.value = Message("update failed")
            }
        }
    }

    private suspend fun update(userId: String, dailyCalories: String) {
        updateUsersDailyCaloriesUseCase.update(userId, dailyCalories)
    }
}