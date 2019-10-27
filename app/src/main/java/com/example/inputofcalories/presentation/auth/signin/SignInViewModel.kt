package com.example.inputofcalories.presentation.auth.signin

import androidx.lifecycle.MutableLiveData
import com.example.inputofcalories.common.rx.HandleError
import com.example.inputofcalories.common.rx.Success
import com.example.inputofcalories.common.rx.SuccessCompletable
import com.example.inputofcalories.domain.user.SaveUserToLocalUseCase
import com.example.inputofcalories.domain.auth.signin.SignInUserUseCase
import com.example.inputofcalories.entity.presentation.Message
import com.example.inputofcalories.entity.register.User
import com.example.inputofcalories.entity.register.UserSignInParams
import com.example.inputofcalories.entity.register.UserType
import com.example.inputofcalories.presentation.viewModel.BaseViewModel

const val SIGN_IN_USER_REQUEST_CODE = 1
const val SAVE_USER_REQUEST_CODE = 2

class SignInViewModel(
    private val singInUserUseCase: SignInUserUseCase,
    private val saveUserToLocalUseCase: SaveUserToLocalUseCase
): BaseViewModel(), HandleError {

    val singInSuccessLiveData = MutableLiveData<User>()

    val singInFailLiveData = MutableLiveData<Message>()

    fun signInClicked(userSignInParams: UserSignInParams) {
        singIn(userSignInParams) { user ->
            saveUser(user) {
                singInSuccessLiveData.value = user
            }
        }
    }

    private fun singIn(userSignInParams: UserSignInParams, success: Success<User>) {
        execute(singInUserUseCase.signIn(userSignInParams),
            requestCode = SIGN_IN_USER_REQUEST_CODE,
            handleError = this,
            success = success)
    }

    private fun saveUser(user: User, success: SuccessCompletable) {
        execute(saveUserToLocalUseCase.save(user),
            requestCode = SAVE_USER_REQUEST_CODE,
            handleError = this,
            success = success)
    }

    override fun invoke(t: Throwable, requestCode: Int?) {
        when(requestCode) {
            SIGN_IN_USER_REQUEST_CODE -> {
                singInFailLiveData.value = Message("failed to sign in")
            }
            SAVE_USER_REQUEST_CODE -> {
                //failed to save user in db, maybe repeat
            }
        }
    }

}