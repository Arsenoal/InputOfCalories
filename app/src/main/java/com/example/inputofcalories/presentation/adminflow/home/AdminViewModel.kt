package com.example.inputofcalories.presentation.adminflow.home

import androidx.lifecycle.liveData
import com.example.inputofcalories.common.exception.UserException
import com.example.inputofcalories.domain.adminflow.AdminFlowUseCase
import com.example.inputofcalories.entity.register.Admin
import com.example.inputofcalories.entity.register.RegularUser
import com.example.inputofcalories.entity.register.UserManager
import com.example.inputofcalories.entity.register.UserType
import com.example.inputofcalories.presentation.adminflow.home.model.entity.UserLoadState
import com.example.inputofcalories.presentation.adminflow.home.model.entity.UserStatusChangeState
import com.example.inputofcalories.presentation.base.BaseViewModel
import com.example.inputofcalories.presentation.common.extensions.switchToDefault
import com.example.inputofcalories.presentation.common.extensions.switchToUi
import kotlinx.coroutines.Dispatchers

class AdminViewModel(
    private val adminFlowUseCase: AdminFlowUseCase
): BaseViewModel() {
    fun loadUsers() = liveData(Dispatchers.Main) {
        switchToDefault {
            try {
                val users = adminFlowUseCase.getUsers()

                if (users.isNotEmpty()) switchToUi { emit(UserLoadState.UsersLoadSucceed(users)) }
                else switchToUi { emit(UserLoadState.NoUsersFound) }
            } catch (ex: UserException) { switchToUi { emit(UserLoadState.UsersLoadFailed) } }
        }
    }

    fun upgradeUser(currentType: UserType, userId: String) = liveData(Dispatchers.Main) {
        switchToDefault {
            try {
                when (currentType) {
                    RegularUser -> {
                        adminFlowUseCase.upgradeToManager(userId)
                        switchToUi { emit(UserStatusChangeState.UserUpgradeSucceed(UserManager)) }
                    }
                    UserManager -> {
                        adminFlowUseCase.upgradeToAdmin(userId)
                        switchToUi { emit(UserStatusChangeState.UserUpgradeSucceed(Admin)) }
                    }
                }
            } catch (ex: UserException) { switchToUi { emit(UserStatusChangeState.UserUpgradeFailed) } }
        }
    }

    fun downgradeUser(currentType: UserType, userId: String) = liveData(Dispatchers.Main) {
        switchToDefault {
            try {
                when (currentType) {
                    UserManager -> {
                        adminFlowUseCase.downgradeToRegular(userId)
                        switchToUi { emit(UserStatusChangeState.UserDowngradeSucceed(RegularUser)) }
                    }
                    Admin -> {
                        adminFlowUseCase.downgradeToManager(userId)
                        switchToUi { emit(UserStatusChangeState.UserDowngradeSucceed(UserManager)) }
                    }
                }
            } catch (ex: UserException) { switchToUi { emit(UserStatusChangeState.UserDowngradeFailed) } }
        }
    }
}