package com.example.inputofcalories.presentation.auth.registration

import androidx.lifecycle.MutableLiveData
import com.example.inputofcalories.common.rx.HandleError
import com.example.inputofcalories.common.rx.Success
import com.example.inputofcalories.common.rx.SuccessCompletable
import com.example.inputofcalories.domain.auth.registration.validation.CheckEmailFormatValidUseCase
import com.example.inputofcalories.domain.auth.registration.validation.CheckPasswordsMatchesUseCase
import com.example.inputofcalories.domain.auth.registration.RegisterUserUseCase
import com.example.inputofcalories.domain.auth.registration.validation.CheckRegistrationFieldsAreFilledUseCase
import com.example.inputofcalories.entity.register.UserRegistrationParams
import com.example.inputofcalories.presentation.viewModel.BaseViewModel

const val REGISTER_USER_REQUEST_CODE = 1

class RegisterUserViewModel(
    private val registerUserUseCase: RegisterUserUseCase,
    private val checkEmailFormatValidUseCase: CheckEmailFormatValidUseCase,
    private val checkPasswordsMatchesUseCase: CheckPasswordsMatchesUseCase,
    private val checkRegistrationFieldsAreFilledUseCase: CheckRegistrationFieldsAreFilledUseCase
): BaseViewModel(), HandleError {

    val userRegistrationSuccessLiveData: MutableLiveData<Any> = MutableLiveData()

    val userRegistrationFailLiveData: MutableLiveData<Any> = MutableLiveData()

    val emailFormatInvalidLiveData: MutableLiveData<Any> = MutableLiveData()

    val passwordsMismatchLiveData: MutableLiveData<Any> = MutableLiveData()

    val notAllFieldsAreFilledLiveData: MutableLiveData<Any> = MutableLiveData()

    fun register(userRegistrationParams: UserRegistrationParams) {

        userRegistrationParams.run {
            checkFieldsAreFilled(userRegistrationParams) { allFieldsAreFilled ->
                if (allFieldsAreFilled) {
                    checkEmailValid(email) { isValid ->
                        if(isValid) {
                            checkPasswordsMatches(password, repeatPassword) { passwordsMatches ->
                                if (passwordsMatches) {
                                    registerUser(this) {
                                        userRegistrationSuccessLiveData.value = Any()
                                    }
                                } else {
                                    passwordsMismatchLiveData.value = Any()
                                }
                            }
                        } else {
                            emailFormatInvalidLiveData.value = Any()
                        }
                    }
                } else {
                    notAllFieldsAreFilledLiveData.value = Any()
                }

            }
        }
    }

    private fun checkEmailValid(email: String, success: Success<Boolean>) {
        execute(checkEmailFormatValidUseCase.check(email),
            success = success)
    }

    private fun checkPasswordsMatches(p1: String, p2: String, success: Success<Boolean>) {
        execute(checkPasswordsMatchesUseCase.check(p1, p2),
            success = success)
    }

    private fun checkFieldsAreFilled(userRegistrationParams: UserRegistrationParams, success: Success<Boolean>) {
        execute(checkRegistrationFieldsAreFilledUseCase.check(userRegistrationParams),
            success = success)
    }

    private fun registerUser(userRegistrationParams: UserRegistrationParams, success: SuccessCompletable) {
        execute(registerUserUseCase.register(userRegistrationParams),
            requestCode = REGISTER_USER_REQUEST_CODE,
            handleError = this,
            success = success)
    }

    override fun invoke(t: Throwable, requestCode: Int?) {
        when(requestCode) {
            REGISTER_USER_REQUEST_CODE -> {
                userRegistrationFailLiveData.value = Any()
            }
        }
    }
}