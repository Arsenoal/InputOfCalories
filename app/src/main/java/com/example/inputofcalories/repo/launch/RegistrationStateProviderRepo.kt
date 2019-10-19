package com.example.inputofcalories.repo.launch

import com.example.inputofcalories.entity.register.UserStatus
import io.reactivex.Single

interface RegistrationStateProviderRepo {
    fun provide(): Single<UserStatus>
}