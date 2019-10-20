package com.example.inputofcalories.common.exception

import com.example.inputofcalories.common.exception.assertion.AssertionHandler
import java.lang.RuntimeException

class InputOfCaloriesException(message: String?): RuntimeException(message)

class InputOfCaloriesResponseException(val reason: String?, message: String?) : RuntimeException(message)

//api exceptions
//auth request exceptions
class RegistrationException(error: Throwable? = null, message: String? = null): RuntimeException(message)

class SignInException(error: Throwable? = null, message: String? = null): RuntimeException(message)

//meal request exceptions
class MealException(error: Throwable? = null, message: String? = null): RuntimeException(message)

fun handleCrashedExceptionOrRunBlock(error: Throwable, block: () -> Unit = {}) {
    //TODO handle all exception cases
    if (error is InputOfCaloriesException) {
        AssertionHandler.onException(error)
    } else {
        block()
    }
}