package com.example.inputofcalories.presentation.regularflow.home

import androidx.lifecycle.MutableLiveData
import com.example.inputofcalories.common.rx.HandleError
import com.example.inputofcalories.common.rx.Success
import com.example.inputofcalories.domain.regularflow.CheckDailyCaloriesDailyLimitUseCase
import com.example.inputofcalories.presentation.viewModel.BaseViewModel

const val CHECK_DAILY_LIMIT_REQUEST_CODE = 1

class CheckDailyLimitViewModel(
    private val checkDailyCaloriesDailyLimitUseCase: CheckDailyCaloriesDailyLimitUseCase
): BaseViewModel(), HandleError {

    val dailyLimitExceededLiveData = MutableLiveData<Any>()

    val dailyLimitNotExceededLiveData = MutableLiveData<Any>()

    val failedToCheckDailyLimitLiveData = MutableLiveData<Any>()

    fun checkDailyLimit() {
        check { limitExceeded ->
            if (limitExceeded) dailyLimitExceededLiveData.value = Any()
            else dailyLimitNotExceededLiveData.value = Any()
        }
    }

    private fun check(success: Success<Boolean>) {
        execute(checkDailyCaloriesDailyLimitUseCase.check(),
            requestCode = CHECK_DAILY_LIMIT_REQUEST_CODE,
            handleError = this,
            success = success)
    }

    override fun invoke(t: Throwable, requestCode: Int?) {
        when(requestCode) {
            CHECK_DAILY_LIMIT_REQUEST_CODE -> {
                failedToCheckDailyLimitLiveData.value = Any()
            }
        }
    }
}