package com.example.inputofcalories.presentation.managerflow.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.inputofcalories.common.exception.UserException
import com.example.inputofcalories.domain.managerflow.DowngradeUserToRegularUseCase
import com.example.inputofcalories.domain.managerflow.UpgradeUserToManagerUseCase
import com.example.inputofcalories.entity.presentation.Message
import com.example.inputofcalories.entity.register.User
import com.example.inputofcalories.presentation.base.BaseViewModel
import kotlinx.coroutines.launch

class ManagerUserStatusManipulatorViewModel(
    private val upgradeUserToManagerUseCase: UpgradeUserToManagerUseCase,
    private val downgradeUserToRegularUseCase: DowngradeUserToRegularUseCase
): BaseViewModel() {

    val userUpgradeSucceedLiveData = MutableLiveData<Any>()

    val userUpgradeFailLiveData = MutableLiveData<Message>()

    val userDowngradeSucceedLiveData = MutableLiveData<Any>()

    val userDowngradeFailLiveData = MutableLiveData<Message>()

    fun upgradeUserClicked(user: User) {
        viewModelScope.launch {
            try {
                upgradeUser(user.id)
                userUpgradeSucceedLiveData.value = Any()
            } catch (ex: UserException) {
                userUpgradeFailLiveData.value = Message("upgrade fail")
            }
        }
    }

    fun downgradeUserClicked(user: User) {
        viewModelScope.launch {
            try {
                downgradeUser(user.id)
                userDowngradeSucceedLiveData.value = Any()
            } catch (ex: UserException) {
                userDowngradeFailLiveData.value = Message("downgrade fail")
            }
        }
    }

    private suspend fun upgradeUser(userId: String) {
        upgradeUserToManagerUseCase.upgrade(userId)
    }

    private suspend fun downgradeUser(userId: String) {
        downgradeUserToRegularUseCase.downgrade(userId)
    }
}