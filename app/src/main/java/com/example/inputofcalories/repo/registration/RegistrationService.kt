package com.example.inputofcalories.repo.registration

import com.example.inputofcalories.common.BaseResponse
import com.example.inputofcalories.repo.registration.model.UserGson
import io.reactivex.Single

interface RegistrationService {
    fun register(userGson: UserGson): Single<BaseResponse<Any>>
}