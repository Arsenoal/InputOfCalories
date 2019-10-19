package com.example.inputofcalories.presentation.registration

import androidx.lifecycle.MutableLiveData
import com.example.inputofcalories.common.rx.HandleError
import com.example.inputofcalories.domain.registration.RegisterUserUseCase
import com.example.inputofcalories.entity.presentation.Message
import com.example.inputofcalories.entity.register.UserRegistrationParams
import com.example.inputofcalories.presentation.viewModel.BaseViewModel

const val REGISTER_USER_REQUEST_CODE = 1

class RegisterUserViewModel(
    private val registerUserUseCase: RegisterUserUseCase
): BaseViewModel(), HandleError {

    val userRegistrationSuccessLiveData: MutableLiveData<Any> = MutableLiveData()

    val userRegistrationFailLiveData: MutableLiveData<Message> = MutableLiveData()

    fun register(userRegistrationParams: UserRegistrationParams) {

        execute(registerUserUseCase.register(userRegistrationParams),
            requestCode = REGISTER_USER_REQUEST_CODE,
            handleError = this) {
            userRegistrationSuccessLiveData.value = Any()
        }
    }

    override fun invoke(t: Throwable, requestCode: Int?) {
        when(requestCode) {
            REGISTER_USER_REQUEST_CODE -> {
                userRegistrationFailLiveData.value = Message(t.message ?: "registration fail")
            }
        }
    }
}