package com.example.inputofcalories.presentation.regularflow.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.inputofcalories.domain.regularflow.CheckDailyCaloriesDailyLimitUseCase
import com.example.inputofcalories.presentation.base.BaseViewModel
import kotlinx.coroutines.launch

class CheckDailyLimitViewModel(
    private val checkDailyCaloriesDailyLimitUseCase: CheckDailyCaloriesDailyLimitUseCase
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

    private suspend fun checkLimitExceeded() = checkDailyCaloriesDailyLimitUseCase.check()
}