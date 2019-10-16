package com.example.inputofcalories.repo.launch

import com.example.inputofcalories.entity.RegistrationStatus
import io.reactivex.Single

interface RegistrationStateProviderRepo {
    fun provide(): Single<RegistrationStatus>
}