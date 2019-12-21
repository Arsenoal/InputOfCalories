package com.example.inputofcalories.presentation.auth.registration

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.inputofcalories.common.exception.RegistrationException
import com.example.inputofcalories.domain.auth.validation.CheckEmailFormatValidUseCase
import com.example.inputofcalories.domain.auth.registration.validation.CheckPasswordsMatchesUseCase
import com.example.inputofcalories.domain.auth.registration.RegisterUserUseCase
import com.example.inputofcalories.domain.auth.registration.validation.CheckRegistrationFieldsAreFilledUseCase
import com.example.inputofcalories.entity.register.UserRegistrationParams
import com.example.inputofcalories.presentation.base.BaseViewModel
import kotlinx.coroutines.launch

class RegisterUserViewModel(
    private val registerUserUseCase: RegisterUserUseCase,
    private val checkEmailFormatValidUseCase: CheckEmailFormatValidUseCase,
    private val checkPasswordsMatchesUseCase: CheckPasswordsMatchesUseCase,
    private val checkRegistrationFieldsAreFilledUseCase: CheckRegistrationFieldsAreFilledUseCase
): BaseViewModel() {

    val userRegistrationSuccessLiveData: MutableLiveData<Any> = MutableLiveData()

    val userRegistrationFailLiveData: MutableLiveData<Any> = MutableLiveData()

    val notAllFieldsAreFilledLiveData: MutableLiveData<Any> = MutableLiveData()

    val emailFormatInvalidLiveData: MutableLiveData<Any> = MutableLiveData()

    val passwordsMismatchLiveData: MutableLiveData<Any> = MutableLiveData()

    fun register(userRegistrationParams: UserRegistrationParams) {

        userRegistrationParams.let { userRegParams ->
            viewModelScope.launch {
                if (allFieldsAreFilled(userRegistrationParams)) {
                    if(isEmailValid(userRegParams.email)) {
                        if(arePasswordMatch(userRegParams.password, userRegParams.repeatPassword)) {
                            try {
                                registerUser(userRegParams)
                                userRegistrationSuccessLiveData.value = Any()
                            } catch (ex: RegistrationException) {
                                userRegistrationFailLiveData.value = Any()
                            }

                        } else {
                            passwordsMismatchLiveData.value = Any()
                        }
                    } else {
                        emailFormatInvalidLiveData.value = Any()
                    }
                } else {
                    notAllFieldsAreFilledLiveData.value = Any()
                }
            }

        }
    }

    private suspend fun isEmailValid(email: String): Boolean {
        return checkEmailFormatValidUseCase.check(email)
    }

    private suspend fun arePasswordMatch(p1: String, p2: String): Boolean {
        return checkPasswordsMatchesUseCase.check(p1, p2)
    }

    private suspend fun allFieldsAreFilled(userRegistrationParams: UserRegistrationParams): Boolean {
        return checkRegistrationFieldsAreFilledUseCase.check(userRegistrationParams)
    }

    private suspend fun registerUser(userRegistrationParams: UserRegistrationParams) {
        registerUserUseCase.register(userRegistrationParams)
    }
}