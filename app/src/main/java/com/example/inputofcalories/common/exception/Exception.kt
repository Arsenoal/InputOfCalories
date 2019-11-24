package com.example.inputofcalories.common.exception

import com.example.inputofcalories.common.exception.assertion.AssertionHandler
import java.lang.RuntimeException

class InputOfCaloriesException(message: String?): RuntimeException(message)

class InputOfCaloriesResponseException(val reason: String?, message: String?) : RuntimeException(message)

//api exceptions
//auth request exceptions

class SignInException(error: Throwable? = null, message: String? = null): RuntimeException(message)

class RegistrationException(error: Throwable? = null, message: String? = null): RuntimeException(message)

//meal request exceptions
class MealException(error: Throwable? = null, message: String? = null): RuntimeException(message)

//user request exception
open class UserException(error: Throwable? = null, message: String? = null): RuntimeException(message)

class UserDailyCaloriesUpdateException(error: Throwable? = null, message: String? = null): UserException(error, message)

class UserDailyCaloriesException(error: Throwable? = null, message: String? = null): UserException(error, message)

//validation
class ValidationException(message: String?): RuntimeException(message)

fun handleCrashedExceptionOrRunBlock(error: Throwable, block: () -> Unit = {}) {
    //TODO handle all exception cases
    if (error is InputOfCaloriesException) {
        AssertionHandler.onException(error)
    } else {
        block()
    }
}