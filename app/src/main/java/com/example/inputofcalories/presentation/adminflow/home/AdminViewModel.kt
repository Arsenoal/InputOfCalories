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
import com.example.inputofcalories.repo.auth.model.TYPE_ADMIN
import com.example.inputofcalories.repo.auth.model.TYPE_MANAGER
import com.example.inputofcalories.repo.auth.model.TYPE_REGULAR
import kotlinx.coroutines.Dispatchers
import java.lang.Exception

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

    fun changeUserType(newType: UserType, userId: String) = liveData(Dispatchers.Main) {
        switchToDefault {
            try {
                when(newType) {
                    RegularUser -> {
                        adminFlowUseCase.changeUserType(userId, TYPE_REGULAR)
                        switchToUi { emit(UserStatusChangeState.UserStatusChangeSucceed) }
                    }
                    UserManager -> {
                        adminFlowUseCase.changeUserType(userId, TYPE_MANAGER)
                        switchToUi { emit(UserStatusChangeState.UserStatusChangeSucceed) }
                    }
                    Admin -> {
                        adminFlowUseCase.changeUserType(userId, TYPE_ADMIN)
                        switchToUi { emit(UserStatusChangeState.UserStatusChangeSucceed) }
                    }
                }
            } catch (ex: Exception) { switchToUi { emit(UserStatusChangeState.UserStatusChangeFailed) } }
        }
    }
}