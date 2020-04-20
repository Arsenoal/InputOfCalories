package com.example.inputofcalories.common.exception

import com.example.inputofcalories.common.exception.assertion.AssertionHandler
import java.lang.RuntimeException

class InputOfCaloriesException(message: String?): RuntimeException(message)

class InputOfCaloriesResponseException(val reason: String?, message: String?) : RuntimeException(message)

//api exceptions
//auth request exceptions

@Suppress("UNUSED_PARAMETER")
class SignInException(error: Throwable? = null, message: String? = null): RuntimeException(message)

@Suppress("UNUSED_PARAMETER")
class RegistrationException(error: Throwable? = null, message: String? = null): RuntimeException(message)

//meal request exceptions
@Suppress("UNUSED_PARAMETER")
class MealException(error: Throwable? = null, message: String? = null): RuntimeException(message)

//user request exception
@Suppress("UNUSED_PARAMETER")
open class UserException(error: Throwable? = null, message: String? = null): RuntimeException(message)

class UserDailyCaloriesUpdateException(error: Throwable? = null, message: String? = null): UserException(error, message)

class UserDailyCaloriesException(error: Throwable? = null, message: String? = null): UserException(error, message)

//validation
@Suppress("UNUSED")
class ValidationException(message: String?): RuntimeException(message)

@Suppress("UNUSED")
fun handleCrashedExceptionOrRunBlock(error: Throwable, block: () -> Unit = {}) {
    //TODO handle all exception cases
    if (error is InputOfCaloriesException) {
        AssertionHandler.onException(error)
    } else {
        block()
    }
}