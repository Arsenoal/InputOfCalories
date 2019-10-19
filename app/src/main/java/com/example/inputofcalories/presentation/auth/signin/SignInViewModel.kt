package com.example.inputofcalories.presentation.auth.signin

import androidx.lifecycle.MutableLiveData
import com.example.inputofcalories.common.rx.HandleError
import com.example.inputofcalories.domain.auth.signin.SignInUserUseCase
import com.example.inputofcalories.entity.presentation.Message
import com.example.inputofcalories.entity.register.UserSignInParams
import com.example.inputofcalories.presentation.viewModel.BaseViewModel

const val SIGN_IN_USER_REQUEST_CODE = 1

class SignInViewModel(
    private val singInUserUseCase: SignInUserUseCase
): BaseViewModel(), HandleError {

    val singInSuccessLiveData = MutableLiveData<Any>()

    val singInFailLiveData = MutableLiveData<Message>()

    fun signIn(userSignInParams: UserSignInParams) {
        execute(singInUserUseCase.signIn(userSignInParams),
            requestCode = SIGN_IN_USER_REQUEST_CODE,
            handleError = this) {
            singInSuccessLiveData.value = Any()
        }
    }

    override fun invoke(t: Throwable, requestCode: Int?) {
        when(requestCode) {
            SIGN_IN_USER_REQUEST_CODE -> {
                singInFailLiveData.value = Message("failed to sign in")
            }
        }
    }
}