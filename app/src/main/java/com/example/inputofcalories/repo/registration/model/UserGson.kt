package com.example.inputofcalories.repo.registration.model

import com.example.inputofcalories.common.extensions.empty
import com.google.gson.annotations.SerializedName

data class UserGson(
    @SerializedName("id") val id: String = String.empty(),
    @SerializedName("name") val name: String = String.empty(),
    @SerializedName("email") val email: String = String.empty(),
    @SerializedName("gender") val gender: String = String.empty()
)