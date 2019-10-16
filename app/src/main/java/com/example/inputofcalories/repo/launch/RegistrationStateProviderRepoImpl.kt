package com.example.inputofcalories.repo.launch

import com.example.inputofcalories.entity.RegistrationStatus
import com.example.inputofcalories.repo.launch.mapper.registrationStateMapper
import com.example.inputofcalories.repo.service.PreferencesService
import io.reactivex.Single

private const val USER_REG_STATE_KEY = "user_registration_state"

class RegistrationStateProviderRepoImpl(
    private val preferencesService: PreferencesService
): RegistrationStateProviderRepo {
    override fun provide(): Single<RegistrationStatus> {
        return Single.fromCallable {
            //TODO getStatus local data
            val stateInt = preferencesService.preference(USER_REG_STATE_KEY, 0)

            registrationStateMapper.map(stateInt)
        }
    }
}