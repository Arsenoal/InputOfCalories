package com.example.inputofcalories.presentation.auth.entity

import com.example.inputofcalories.entity.presentation.Message
import com.example.inputofcalories.entity.register.User

sealed class SignInState

object NotAllFieldsForSignInFilled: SignInState()

object InvalidSignInEmailFormat: SignInState()

class SignInFailed(val message: Message): SignInState()

class SignInSucceed(val user: User): SignInState()