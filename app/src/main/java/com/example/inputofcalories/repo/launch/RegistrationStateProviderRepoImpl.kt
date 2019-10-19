package com.example.inputofcalories.repo.launch

import com.example.inputofcalories.entity.register.UserStatus
import com.example.inputofcalories.repo.launch.mapper.registrationStateMapper
import com.example.inputofcalories.repo.common.service.preferences.PreferencesService
import io.reactivex.Single

private const val USER_REG_STATE_KEY = "user_registration_state"

class RegistrationStateProviderRepoImpl(
    private val preferencesService: PreferencesService
): RegistrationStateProviderRepo {
    override fun provide(): Single<UserStatus> {
        return Single.fromCallable {
            //TODO getStatus local data
            val stateInt = preferencesService.preference(USER_REG_STATE_KEY, 0)

            registrationStateMapper.map(stateInt)
        }
    }
}