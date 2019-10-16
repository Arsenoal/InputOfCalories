package com.example.inputofcalories.presentation.launch

import androidx.lifecycle.MutableLiveData
import com.example.inputofcalories.common.rx.HandleError
import com.example.inputofcalories.domain.launch.GetUserRegistrationStatusUseCase
import com.example.inputofcalories.entity.Admin
import com.example.inputofcalories.entity.NotRegistered
import com.example.inputofcalories.entity.RegularUser
import com.example.inputofcalories.entity.UserManager
import com.example.inputofcalories.presentation.BaseViewModel

private const val GET_REGISTRATION_STATUS_CODE = 1

class LauncherViewModel(
    private val getUserRegistrationStatusUseCase: GetUserRegistrationStatusUseCase
): BaseViewModel(), HandleError {

    var statusUserNotRegisteredLiveData: MutableLiveData<Any> = MutableLiveData()

    var statusRegularUserLiveData: MutableLiveData<Any> = MutableLiveData()

    var statusUserManagerLiveData: MutableLiveData<Any> = MutableLiveData()

    var statusAdminLiveData: MutableLiveData<Any> = MutableLiveData()

    fun onLaunch() {
        execute(
            getUserRegistrationStatusUseCase.getStatus(),
            requestCode = GET_REGISTRATION_STATUS_CODE,
            handleError = this) {
            when(it) {
                NotRegistered -> { statusUserNotRegisteredLiveData.value = Any() }
                RegularUser -> { statusRegularUserLiveData.value = Any() }
                UserManager -> { statusUserManagerLiveData.value = Any() }
                Admin -> { statusAdminLiveData.value = Any() }
            }
        }
    }

    override fun invoke(t: Throwable, requestCode: Int?) {
        when(requestCode) {
            GET_REGISTRATION_STATUS_CODE -> {
                statusUserNotRegisteredLiveData.value = Any()
            }
        }
    }
}