package com.example.inputofcalories.presentation.auth.signin

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.inputofcalories.common.exception.SignInException
import com.example.inputofcalories.domain.user.SaveUserToLocalUseCase
import com.example.inputofcalories.domain.auth.signin.SignInUserUseCase
import com.example.inputofcalories.domain.auth.signin.validation.CheckSignInFieldsAreFilledUseCase
import com.example.inputofcalories.domain.auth.validation.CheckEmailFormatValidUseCase
import com.example.inputofcalories.entity.register.User
import com.example.inputofcalories.entity.register.UserSignInParams
import com.example.inputofcalories.presentation.base.BaseViewModel
import kotlinx.coroutines.launch

class SignInViewModel(
    private val singInUserUseCase: SignInUserUseCase,
    private val saveUserToLocalUseCase: SaveUserToLocalUseCase,
    private val checkSignInFieldsAreFilledUseCase: CheckSignInFieldsAreFilledUseCase,
    private val checkEmailFormatValidUseCase: CheckEmailFormatValidUseCase
): BaseViewModel() {

    val singInSuccessLiveData = MutableLiveData<User>()

    val singInFailLiveData = MutableLiveData<Any>()

    val notAllFieldsAreFilledLiveData: MutableLiveData<Any> = MutableLiveData()

    val emailFormatInvalidLiveData: MutableLiveData<Any> = MutableLiveData()

    fun signInClicked(userSignInParams: UserSignInParams) {
        viewModelScope.launch {
            userSignInParams.let { userSignInParams ->
                if (allFieldsAreFilled(userSignInParams)) {
                    if(isEmailFormatValid(userSignInParams.email)) {
                        try {
                            val user = singIn(userSignInParams)
                            saveUser(user)
                            singInSuccessLiveData.value = user
                        } catch (ex: SignInException) {
                            singInFailLiveData.value = Any()
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

    private suspend fun allFieldsAreFilled(userSignInParams: UserSignInParams): Boolean {
        return checkSignInFieldsAreFilledUseCase.check(userSignInParams)
    }

    private suspend fun isEmailFormatValid(email: String): Boolean {
        return checkEmailFormatValidUseCase.check(email)
    }

    private suspend fun singIn(userSignInParams: UserSignInParams): User {
        return singInUserUseCase.signIn(userSignInParams)
    }

    private suspend fun saveUser(user: User) {
        saveUserToLocalUseCase.save(user)
    }

}