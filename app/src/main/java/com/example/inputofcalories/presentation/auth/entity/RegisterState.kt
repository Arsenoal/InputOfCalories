package com.example.inputofcalories.presentation.auth.entity

import com.example.inputofcalories.entity.presentation.Message

sealed class RegisterState

object NotAllFieldsFilledForRegister: RegisterState()

object InvalidRegisterEmailFormat: RegisterState()

object PasswordsMismatch: RegisterState()

class RegisterFailed(val message: Message): RegisterState()

object RegisterSucceed: RegisterState()

