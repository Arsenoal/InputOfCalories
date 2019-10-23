package com.example.inputofcalories.presentation.managerflow.home

import androidx.lifecycle.MutableLiveData
import com.example.inputofcalories.common.rx.HandleError
import com.example.inputofcalories.common.rx.SuccessCompletable
import com.example.inputofcalories.domain.managerflow.DowngradeUserToRegularUseCase
import com.example.inputofcalories.domain.managerflow.UpgradeUserToManagerUseCase
import com.example.inputofcalories.entity.presentation.Message
import com.example.inputofcalories.entity.register.User
import com.example.inputofcalories.presentation.viewModel.BaseViewModel

const val UPGRADE_USER_REQUEST_CODE = 1
const val DOWNGRADE_USER_REQUEST_CODE = 2

class UserStatusManipulatorViewModel(
    private val upgradeUserToManagerUseCase: UpgradeUserToManagerUseCase,
    private val downgradeUserToRegularUseCase: DowngradeUserToRegularUseCase): BaseViewModel(), HandleError {

    val userUpgradeSucceedLiveData = MutableLiveData<Any>()

    val userUpgradeFailLiveData = MutableLiveData<Message>()

    val userDowngradeSucceedLiveData = MutableLiveData<Any>()

    val userDowngradeFailLiveData = MutableLiveData<Message>()

    fun upgradeUserClicked(user: User) {
        upgradeUser(user.id) {
            userUpgradeSucceedLiveData.value = Any()
        }
    }

    fun downgradeUserClicked(user: User) {
        downgradeUser(user.id) {
            userDowngradeSucceedLiveData.value = Any()
        }
    }

    private fun upgradeUser(userId: String, success: SuccessCompletable) {
        execute(upgradeUserToManagerUseCase.upgrade(userId),
            requestCode = UPGRADE_USER_REQUEST_CODE,
            handleError = this,
            success = success)
    }

    private fun downgradeUser(userId: String, success: SuccessCompletable) {
        execute(downgradeUserToRegularUseCase.downgrade(userId),
            requestCode = UPGRADE_USER_REQUEST_CODE,
            handleError = this,
            success = success)
    }

    override fun invoke(t: Throwable, requestCode: Int?) {
        when(requestCode) {
            UPGRADE_USER_REQUEST_CODE -> {
                userUpgradeFailLiveData.value = Message("upgrade fail")
            }
            DOWNGRADE_USER_REQUEST_CODE -> {
                userDowngradeFailLiveData.value = Message("downgrade fail")
            }
        }
    }
}