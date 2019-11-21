package com.example.inputofcalories.presentation.auth.signin

import androidx.lifecycle.MutableLiveData
import com.example.inputofcalories.common.rx.HandleError
import com.example.inputofcalories.common.rx.Success
import com.example.inputofcalories.common.rx.SuccessCompletable
import com.example.inputofcalories.domain.user.SaveUserToLocalUseCase
import com.example.inputofcalories.domain.auth.signin.SignInUserUseCase
import com.example.inputofcalories.domain.auth.signin.validation.CheckSignInFieldsAreFilledUseCase
import com.example.inputofcalories.domain.auth.validation.CheckEmailFormatValidUseCase
import com.example.inputofcalories.entity.register.User
import com.example.inputofcalories.entity.register.UserSignInParams
import com.example.inputofcalories.presentation.viewModel.BaseViewModel

const val SIGN_IN_USER_REQUEST_CODE = 1
const val SAVE_USER_REQUEST_CODE = 2

class SignInViewModel(
    private val singInUserUseCase: SignInUserUseCase,
    private val saveUserToLocalUseCase: SaveUserToLocalUseCase,
    private val checkSignInFieldsAreFilledUseCase: CheckSignInFieldsAreFilledUseCase,
    private val checkEmailFormatValidUseCase: CheckEmailFormatValidUseCase
): BaseViewModel(), HandleError {

    val singInSuccessLiveData = MutableLiveData<User>()

    val singInFailLiveData = MutableLiveData<Any>()

    val notAllFieldsAreFilledLiveData: MutableLiveData<Any> = MutableLiveData()

    val emailFormatInvalidLiveData: MutableLiveData<Any> = MutableLiveData()

    fun signInClicked(userSignInParams: UserSignInParams) {
        userSignInParams.run {
            checkFieldsAreFilled(this) { fieldsAreFilled ->
                if(fieldsAreFilled) {
                    checkEmailFormatValid(email) { emailFormatIsValid ->
                        if (emailFormatIsValid) {
                            singIn(this) { user ->
                                saveUser(user) {
                                    singInSuccessLiveData.value = user
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

    private fun checkFieldsAreFilled(userSignInParams: UserSignInParams, success: Success<Boolean>) {
        execute(checkSignInFieldsAreFilledUseCase.check(userSignInParams),
            success = success)
    }

    private fun checkEmailFormatValid(email: String, success: Success<Boolean>) {
        execute(checkEmailFormatValidUseCase.check(email),
            success = success)
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
                singInFailLiveData.value = Any()
            }
            SAVE_USER_REQUEST_CODE -> {
                //failed to save user in db, maybe repeat
            }
        }
    }

}