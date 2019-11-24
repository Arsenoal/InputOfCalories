package com.example.inputofcalories.presentation.launch

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.inputofcalories.common.logger.IOCLogger
import com.example.inputofcalories.domain.launch.InvalidateLocalDataOnAppOpenUseCase
import com.example.inputofcalories.presentation.base.BaseViewModel
import kotlinx.coroutines.launch
import java.lang.Exception

class InvalidateLocalDataViewModel(
    private val invalidateLocalDataOnAppOpenUseCase: InvalidateLocalDataOnAppOpenUseCase
): BaseViewModel() {

    val dataClearSucceedLiveData = MutableLiveData<Any>()

    fun clearLocalData() {
        viewModelScope.launch {
            try {
                clearRoom()
                dataClearSucceedLiveData.value = Any()
            } catch (ex: Exception) {
                IOCLogger.d(InvalidateLocalDataViewModel::class.java.name, "failed to clear room data")
            }
        }
    }

    private suspend fun clearRoom() {
        invalidateLocalDataOnAppOpenUseCase.invalidate()
    }
}