package com.example.inputofcalories.presentation.auth.signin

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.inputofcalories.domain.auth.AuthUseCase
import com.example.inputofcalories.domain.user.SaveUserToLocalUseCase
import com.example.inputofcalories.domain.auth.validation.AuthValidationUseCase
import com.example.inputofcalories.entity.presentation.Message
import com.example.inputofcalories.entity.register.User
import com.example.inputofcalories.entity.register.UserSignInParams
import com.example.inputofcalories.presentation.base.BaseViewModel
import kotlinx.coroutines.launch

class SignInViewModel(
    private val authUseCase: AuthUseCase,
    private val saveUserToLocalUseCase: SaveUserToLocalUseCase,
    private val authValidationUseCase: AuthValidationUseCase
): BaseViewModel() {

    val singInSuccessLiveData = MutableLiveData<User>()

    val singInFailLiveData = MutableLiveData<Message>()

    val notAllFieldsAreFilledLiveData: MutableLiveData<Any> = MutableLiveData()

    val emailFormatInvalidLiveData: MutableLiveData<Any> = MutableLiveData()

    fun signInClicked(userSignInParams: UserSignInParams) {
        viewModelScope.launch {
            try {
                if (allFieldsAreFilled(userSignInParams)) {
                    if(isEmailFormatValid(userSignInParams.email)) {
                        singIn(userSignInParams)?.let { user ->
                            saveUser(user)
                            singInSuccessLiveData.value = user
                        }
                    } else { emailFormatInvalidLiveData.value = Any() }
                } else { notAllFieldsAreFilledLiveData.value = Any() }
            } catch (ex: Exception) {
                ex.message?.let { message -> singInFailLiveData.value = Message(text = message) }
            }
        }
    }

    private suspend fun allFieldsAreFilled(userSignInParams: UserSignInParams) = authValidationUseCase.checkSignInFieldsAreFilled(userSignInParams)

    private suspend fun isEmailFormatValid(email: String) = authValidationUseCase.checkEmailFormat(email)

    private suspend fun singIn(userSignInParams: UserSignInParams) = authUseCase.signIn(userSignInParams)

    private suspend fun saveUser(user: User) = saveUserToLocalUseCase.save(user)

}