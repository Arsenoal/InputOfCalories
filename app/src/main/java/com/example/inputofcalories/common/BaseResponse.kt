package com.example.inputofcalories.common

import com.google.gson.annotations.SerializedName

data class BaseResponse<T>(
        @SerializedName("status")
        val status: String,
        @SerializedName("response")
        val response: T? = null,
        @SerializedName("reason")
        val reason: String? = null,
        @SerializedName("text")
        val message: String? = null)