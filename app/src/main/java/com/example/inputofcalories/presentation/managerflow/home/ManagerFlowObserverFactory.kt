package com.example.inputofcalories.presentation.managerflow.home

import android.view.View.VISIBLE
import androidx.lifecycle.Observer
import com.example.inputofcalories.R
import com.example.inputofcalories.entity.register.RegularUser
import com.example.inputofcalories.entity.register.UserManager
import com.example.inputofcalories.presentation.common.ToastManager
import com.example.inputofcalories.presentation.managerflow.home.model.UserTypeChangeParams
import com.example.inputofcalories.presentation.managerflow.home.model.entity.UserLoadState
import com.example.inputofcalories.presentation.managerflow.home.model.entity.UserStatusChangeState
import kotlinx.android.synthetic.main.activity_manager_user_home.*

class ManagerFlowObserverFactory(private val managerUserHomeActivity: ManagerUserHomeActivity) {

    private val getUsersObserver = Observer<UserLoadState> { state ->
        when(state) {
            UserLoadState.UsersLoadFailed -> {
                ToastManager.showToastShort(managerUserHomeActivity, managerUserHomeActivity.resources.getString(R.string.failed_to_load_users))
            }
            UserLoadState.NoUsersFound -> {
                managerUserHomeActivity.noUsersToShowTextView.visibility = VISIBLE
            }
            is UserLoadState.UsersLoadSucceed -> {
                managerUserHomeActivity.usersRecyclerAdapter.setItems(state.users)
            }
        }
    }

    private fun upgradeUserObserver(params: UserTypeChangeParams) = Observer<UserStatusChangeState> { state ->
        when(state) {
            UserStatusChangeState.UserUpgradeSucceed -> {
                //managerUserHomeActivity.usersRecyclerAdapter.updateUserStatus(UserManager, params.adapterPosition)
            }
            UserStatusChangeState.UserUpgradeFailed -> {
                managerUserHomeActivity.usersRecyclerAdapter.updateUserStatus(RegularUser, params.adapterPosition)
                ToastManager.showToastShort(managerUserHomeActivity, managerUserHomeActivity.getString(R.string.upgrade_fail))
            }
        }
    }

    private fun downgradeUserObserver(params: UserTypeChangeParams) = Observer<UserStatusChangeState> { state ->
        when(state) {
            UserStatusChangeState.UserDowngradeSucceed -> {
                //managerUserHomeActivity.usersRecyclerAdapter.updateUserStatus(RegularUser, params.adapterPosition)
            }
            UserStatusChangeState.UserDowngradeFailed -> {
                managerUserHomeActivity.usersRecyclerAdapter.updateUserStatus(UserManager, params.adapterPosition)
                ToastManager.showToastShort(managerUserHomeActivity, managerUserHomeActivity.getString(R.string.downgrade_fail))
            }
        }
    }

    @Suppress("UNCHECKED_CAST")
    fun <T> get(key: ObserverKey): Observer<in T> = when(key) {
        ObserverKey.GetUsersObserver -> { getUsersObserver as Observer<in T> }
        is ObserverKey.UpgradeUserObserver -> { upgradeUserObserver(key.params) as Observer<in T> }
        is ObserverKey.DowngradeUserObserver -> { downgradeUserObserver(key.params) as Observer<in T> }
    }

    sealed class ObserverKey {
        object GetUsersObserver: ObserverKey()
        class UpgradeUserObserver(val params: UserTypeChangeParams): ObserverKey()
        class DowngradeUserObserver(val params: UserTypeChangeParams): ObserverKey()
    }
}