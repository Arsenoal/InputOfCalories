package com.example.inputofcalories.presentation.managerflow.home

import androidx.lifecycle.liveData
import com.example.inputofcalories.common.exception.UserException
import com.example.inputofcalories.domain.managerflow.MangerFlowUseCase
import com.example.inputofcalories.presentation.base.BaseViewModel
import com.example.inputofcalories.presentation.common.extensions.switchToDefault
import com.example.inputofcalories.presentation.common.extensions.switchToUi
import com.example.inputofcalories.presentation.managerflow.home.model.entity.UserLoadState
import com.example.inputofcalories.presentation.managerflow.home.model.entity.UserStatusChangeState
import kotlinx.coroutines.Dispatchers

class ManagerViewModel(
    private val managerFlowUseCase: MangerFlowUseCase
): BaseViewModel() {

    fun getUsers() = liveData(Dispatchers.Main) {
        switchToDefault {
            try {
                val users = managerFlowUseCase.getUsers()

                if (users.isNotEmpty()) switchToUi { emit(UserLoadState.UsersLoadSucceed(users)) }
                else switchToUi { emit(UserLoadState.NoUsersFound) }
            } catch (ex: UserException) { switchToUi { emit(UserLoadState.UsersLoadFailed) } }
        }
    }

    fun upgradeUser(userId: String) = liveData(Dispatchers.Main) {
        switchToDefault {
            try {
                managerFlowUseCase.upgradeToManager(userId)
                switchToUi { emit(UserStatusChangeState.UserUpgradeSucceed) }
            } catch (ex: UserException) { switchToUi { emit(UserStatusChangeState.UserUpgradeFailed) } }
        }
    }

    fun downgradeUser(userId: String) = liveData(Dispatchers.Main) {
        switchToDefault {
            try {
                managerFlowUseCase.downgradeToRegular(userId)
                switchToUi { emit(UserStatusChangeState.UserDowngradeSucceed) }
            } catch (ex: UserException) { switchToUi { emit(UserStatusChangeState.UserDowngradeFailed) } }
        }
    }
}