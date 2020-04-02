package com.example.inputofcalories.presentation.auth

import androidx.lifecycle.liveData
import com.example.inputofcalories.common.extensions.empty
import com.example.inputofcalories.domain.auth.AuthUseCase
import com.example.inputofcalories.domain.auth.validation.AuthValidationUseCase
import com.example.inputofcalories.domain.user.UserUseCase
import com.example.inputofcalories.entity.register.UserRegistrationParams
import com.example.inputofcalories.entity.register.UserSignInParams
import com.example.inputofcalories.presentation.auth.entity.*
import com.example.inputofcalories.presentation.base.BaseViewModel
import com.example.inputofcalories.entity.presentation.Message
import com.example.inputofcalories.presentation.common.extensions.switchToDefault
import com.example.inputofcalories.presentation.common.extensions.switchToUi
import kotlinx.coroutines.Dispatchers
import java.lang.Exception

class AuthViewModel(
    private val authUseCase: AuthUseCase,
    private val userUseCase: UserUseCase,
    private val authValidationUseCase: AuthValidationUseCase
): BaseViewModel() {

    fun register(userRegistrationParams: UserRegistrationParams) = liveData(Dispatchers.Main) {
        switchToDefault {
            if(authValidationUseCase.checkRegistrationFieldsAreFilled(userRegistrationParams)) {
                if(authValidationUseCase.checkEmailFormat(userRegistrationParams.email)) {
                    if(authValidationUseCase.checkPasswordsMatch(userRegistrationParams.password, userRegistrationParams.repeatPassword)) {
                        try {
                            authUseCase.register(userRegistrationParams)
                            switchToUi { emit(RegisterSucceed) }
                        } catch (ex: Exception) { switchToUi { emit(RegisterFailed(Message(ex.message ?: String.empty()))) } }
                    } else { switchToUi { emit(PasswordsMismatch) } }
                } else { switchToUi { emit(InvalidRegisterEmailFormat) } }
            } else { switchToUi { emit(NotAllFieldsFilledForRegister) } }
        }
    }

    fun signIn(userSignInParams: UserSignInParams) = liveData(Dispatchers.Main) {
        switchToDefault {
            if(authValidationUseCase.checkSignInFieldsAreFilled(userSignInParams)) {
                if(authValidationUseCase.checkEmailFormat(userSignInParams.email)) {
                    try {
                        authUseCase.signIn(userSignInParams)?.let { user ->
                            userUseCase.set(user)
                            switchToUi { emit(SignInSucceed(user)) }
                        }
                    } catch (ex: Exception) { switchToUi { emit(SignInFailed(Message(ex.message ?: String.empty()))) } }
                } else { switchToUi { emit(InvalidSignInEmailFormat) } }
            } else { switchToUi { emit(NotAllFieldsForSignInFilled) } }
        }
    }
}