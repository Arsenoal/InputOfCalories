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
            downgradeUser(user)
            userDowngradeSucceedLiveData.value = Any()
        }
    }

    private suspend fun upgradeUser(user: User) {
        try {
            when (user.userParams.type) {
                RegularUser -> { upgradeToManager(user.id) }
                UserManager -> { upgradeToAdmin(user.id) }
            }
        } catch (e: UserException) {
            userUpgradeFailLiveData.value = Any()
        }
    }

    private suspend fun upgradeToManager(userId: String) { adminFlowUseCase.upgradeToManager(userId) }

    private suspend fun upgradeToAdmin(userId: String) { adminFlowUseCase.upgradeToAdmin(userId) }

    private suspend fun downgradeUser(user: User) {
        try {
            when (user.userParams.type) {
                UserManager -> { downgradeToRegular(user.id) }
                Admin -> { downgradeToManager(user.id) }
            }
        } catch (e: UserException) {
            userDowngradeFailLiveData.value = Any()
        }
    }

    private suspend fun downgradeToRegular(userId: String) { adminFlowUseCase.downgradeToRegular(userId) }

    private suspend fun downgradeToManager(userId: String) { adminFlowUseCase.downgradeToManager(userId) }
}