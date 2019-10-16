package com.example.inputofcalories.common.mapper

import com.example.inputofcalories.common.BaseResponse
import com.example.inputofcalories.common.exception.InputOfCaloriesResponseException

class ExceptionMapper<T> {

    @Throws(Exception::class)
    fun map(baseResponse: BaseResponse<T>): T? {
        return unwrap(baseResponse)
    }

    @Throws(Exception::class)
    private fun unwrap(baseResponse: BaseResponse<T>): T? {
        baseResponse.run {
            return when(status) {
                "success" -> response
                else -> throw InputOfCaloriesResponseException(reason, message)
            }
        }
    }
}