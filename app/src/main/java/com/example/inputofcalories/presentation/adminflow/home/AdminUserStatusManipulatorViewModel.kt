package com.example.inputofcalories.presentation.adminflow.home

import androidx.lifecycle.MutableLiveData
import com.example.inputofcalories.common.rx.HandleError
import com.example.inputofcalories.common.rx.SuccessCompletable
import com.example.inputofcalories.domain.adminflow.DowngradeUserToManagerUseCase
import com.example.inputofcalories.domain.adminflow.UpgradeUserToAdminUseCase
import com.example.inputofcalories.domain.managerflow.DowngradeUserToRegularUseCase
import com.example.inputofcalories.domain.managerflow.UpgradeUserToManagerUseCase
import com.example.inputofcalories.entity.presentation.Message
import com.example.inputofcalories.entity.register.Admin
import com.example.inputofcalories.entity.register.RegularUser
import com.example.inputofcalories.entity.register.User
import com.example.inputofcalories.entity.register.UserManager
import com.example.inputofcalories.presentation.viewModel.BaseViewModel

const val UPGRADE_USER_REQUEST_CODE = 1
const val DOWNGRADE_USER_REQUEST_CODE = 2

class AdminUserStatusManipulatorViewModel(
    private val upgradeUserToAdminUseCase: UpgradeUserToAdminUseCase,
    private val downgradeUserToManagerUseCase: DowngradeUserToManagerUseCase,
    private val upgradeUserToManagerUseCase: UpgradeUserToManagerUseCase,
    private val downgradeUserToRegularUseCase: DowngradeUserToRegularUseCase
): BaseViewModel(), HandleError {

    val userUpgradeSucceedLiveData = MutableLiveData<Any>()

    val userUpgradeFailLiveData = MutableLiveData<Message>()

    val userDowngradeSucceedLiveData = MutableLiveData<Any>()

    val userDowngradeFailLiveData = MutableLiveData<Message>()

    fun upgradeUserClicked(user: User) {
        upgradeUser(user) {
            userUpgradeSucceedLiveData.value = Any()
        }
    }

    fun downgradeUserClicked(user: User) {
        downgradeUser(user) {
            userDowngradeSucceedLiveData.value = Any()
        }
    }

    private fun upgradeUser(user: User, success: SuccessCompletable) {
        when(user.userParams.type) {
            RegularUser -> { upgradeToManager(user.id, success) }
            UserManager -> { upgradeToAdmin(user.id, success) }
        }
    }

    private fun upgradeToManager(userId: String, success: SuccessCompletable) {
        execute(upgradeUserToManagerUseCase.upgrade(userId),
            requestCode = UPGRADE_USER_REQUEST_CODE,
            handleError = this,
            success = success)
    }

    private fun upgradeToAdmin(userId: String, success: SuccessCompletable) {
        execute(upgradeUserToAdminUseCase.upgrade(userId),
            requestCode = UPGRADE_USER_REQUEST_CODE,
            handleError = this,
            success = success)
    }

    private fun downgradeUser(user: User, success: SuccessCompletable) {
        when(user.userParams.type) {
            UserManager -> { downgradeToRegular(user.id, success) }
            Admin -> { downgradeToManager(user.id, success) }
        }
    }

    private fun downgradeToRegular(userId: String, success: SuccessCompletable) {
        execute(downgradeUserToRegularUseCase.downgrade(userId),
            requestCode = DOWNGRADE_USER_REQUEST_CODE,
            handleError = this,
            success = success)
    }

    private fun downgradeToManager(userId: String, success: SuccessCompletable) {
        execute(downgradeUserToManagerUseCase.downgrade(userId),
            requestCode = DOWNGRADE_USER_REQUEST_CODE,
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