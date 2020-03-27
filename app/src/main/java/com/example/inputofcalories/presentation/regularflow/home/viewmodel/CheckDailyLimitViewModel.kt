package com.example.inputofcalories.presentation.regularflow.home.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.inputofcalories.domain.regularflow.dailycalories.DailyCaloriesUseCase
import com.example.inputofcalories.presentation.base.BaseViewModel
import kotlinx.coroutines.launch

class CheckDailyLimitViewModel(
    private val dailyCaloriesUseCase: DailyCaloriesUseCase
): BaseViewModel() {

    val dailyLimitExceededLiveData = MutableLiveData<Any>()

    val dailyLimitNotExceededLiveData = MutableLiveData<Any>()

    val failedToCheckDailyLimitLiveData = MutableLiveData<Any>()

    fun checkDailyLimit() {
        viewModelScope.launch {
            val limitExceeded = checkLimitExceeded()

            if (limitExceeded) dailyLimitExceededLiveData.value = Any()
            else dailyLimitNotExceededLiveData.value = Any()
        }
    }

    private suspend fun checkLimitExceeded() = dailyCaloriesUseCase.isLimitExceeded()
}