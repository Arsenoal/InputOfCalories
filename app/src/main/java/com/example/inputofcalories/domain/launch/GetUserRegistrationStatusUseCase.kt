package com.example.inputofcalories.domain.launch

import com.example.inputofcalories.entity.UserStatus
import io.reactivex.Single

interface GetUserRegistrationStatusUseCase {
    fun getStatus(): Single<UserStatus>
}