package com.example.inputofcalories.presentation.adminflow.home.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.inputofcalories.common.exception.UserException
import com.example.inputofcalories.domain.adminflow.*
import com.example.inputofcalories.entity.register.Admin
import com.example.inputofcalories.entity.register.RegularUser
import com.example.inputofcalories.entity.register.User
import com.example.inputofcalories.entity.register.UserManager
import com.example.inputofcalories.presentation.base.BaseViewModel
import kotlinx.coroutines.launch

class AdminUserStatusManipulatorViewModel(
    private val adminFlowUseCase: AdminFlowUseCase
): BaseViewModel() {

    val userUpgradeSucceedLiveData = MutableLiveData<Any>()

    val userUpgradeFailLiveData = MutableLiveData<Any>()

    val userDowngradeSucceedLiveData = MutableLiveData<Any>()

    val userDowngradeFailLiveData = MutableLiveData<Any>()

    fun upgradeUserClicked(user: User) {
        viewModelScope.launch {
            upgradeUser(user)
            userUpgradeSucceedLiveData.value = Any()
        }
    }

    fun downgradeUserClicked(user: User) {
        viewModelScope.launch {
            try {
                when (user.userParams.type) {
                    UserManager -> { adminFlowUseCase.downgradeToRegular(user.id) }
                    Admin -> { adminFlowUseCase.downgradeToManager(user.id) }
                }

                userDowngradeSucceedLiveData.value = Any()
            } catch (e: UserException) {
                userDowngradeFailLiveData.value = Any()
            }
        }
    }

    private suspend fun upgradeUser(user: User) {
        try {
            when (user.userParams.type) {
                RegularUser -> { adminFlowUseCase.upgradeToManager(user.id) }
                UserManager -> { adminFlowUseCase.upgradeToAdmin(user.id) }
            }
        } catch (e: UserException) {
            userUpgradeFailLiveData.value = Any()
        }
    }
}