package com.example.inputofcalories.presentation.launch

import androidx.lifecycle.MutableLiveData
import com.example.inputofcalories.common.logger.IOCLogger
import com.example.inputofcalories.common.rx.HandleError
import com.example.inputofcalories.common.rx.SuccessCompletable
import com.example.inputofcalories.domain.launch.InvalidateLocalDataOnAppOpenUseCase
import com.example.inputofcalories.presentation.viewModel.BaseViewModel

private const val CLEAR_ROOM_DATA_REQUEST_CODE = 1

class InvalidateLocalDataViewModel(
    private val invalidateLocalDataOnAppOpenUseCase: InvalidateLocalDataOnAppOpenUseCase
): BaseViewModel(), HandleError {

    val dataClearSucceedLiveData = MutableLiveData<Any>()

    fun clearLocalData() {
        clearRoom {
            dataClearSucceedLiveData.value = Any()
        }
    }

    private fun clearRoom(success: SuccessCompletable) {
        execute(invalidateLocalDataOnAppOpenUseCase.invalidate(),
            requestCode = CLEAR_ROOM_DATA_REQUEST_CODE,
            handleError = this,
            success = success)
    }

    override fun invoke(t: Throwable, requestCode: Int?) {
        when(requestCode) {
            CLEAR_ROOM_DATA_REQUEST_CODE -> {
                IOCLogger.d(InvalidateLocalDataViewModel::class.java.name, "failed to clear room data")
            }
        }
    }
}