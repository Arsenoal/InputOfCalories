package com.example.inputofcalories.domain.launch

import com.example.inputofcalories.entity.RegistrationStatus
import io.reactivex.Single

interface GetUserRegistrationStatusUseCase {
    fun getStatus(): Single<RegistrationStatus>
}