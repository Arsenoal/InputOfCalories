package com.example.inputofcalories.repo.launch

import com.example.inputofcalories.entity.UserStatus
import io.reactivex.Single

interface RegistrationStateProviderRepo {
    fun provide(): Single<UserStatus>
}