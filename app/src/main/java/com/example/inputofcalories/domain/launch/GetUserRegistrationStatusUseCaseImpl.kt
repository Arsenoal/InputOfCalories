package com.example.inputofcalories.domain.launch

import com.example.inputofcalories.repo.launch.RegistrationStateProviderRepo

class GetUserRegistrationStatusUseCaseImpl(
    private val registrationStateProviderRepo: RegistrationStateProviderRepo
): GetUserRegistrationStatusUseCase {
    override fun getStatus() = registrationStateProviderRepo.provide()
}