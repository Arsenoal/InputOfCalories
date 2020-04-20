package com.example.inputofcalories.presentation.managerflow.home

import android.os.Bundle
import android.view.View.VISIBLE
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.inputofcalories.R
import com.example.inputofcalories.entity.register.RegularUser
import com.example.inputofcalories.entity.register.UserManager
import com.example.inputofcalories.presentation.base.BaseActivity
import com.example.inputofcalories.presentation.common.ToastManager
import com.example.inputofcalories.presentation.managerflow.home.model.entity.UserLoadState
import com.example.inputofcalories.presentation.managerflow.home.model.entity.UserStatusChangeState
import kotlinx.android.synthetic.main.activity_manager_user_home.*
import org.koin.android.viewmodel.ext.android.viewModel

class ManagerUserHomeActivity: BaseActivity() {

    private val managerViewModel: ManagerViewModel by viewModel()

    private val usersRecyclerAdapter = UsersRecyclerAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_manager_user_home)

        loadUsers()

        setupUsersRecyclerView()
    }

    private fun loadUsers() {
        managerViewModel.getUsers().observe(this, Observer { state ->
            when(state) {
                UserLoadState.UsersLoadFailed -> {
                    ToastManager.showToastShort(this, resources.getString(R.string.failed_to_load_users))
                }
                UserLoadState.NoUsersFound -> {
                    noUsersToShowTextView.visibility = VISIBLE
                }
                is UserLoadState.UsersLoadSucceed -> {
                    usersRecyclerAdapter.setItems(state.users)
                }
            }
        })
    }

    private fun setupUsersRecyclerView() {
        usersRecyclerView.layoutManager = LinearLayoutManager(this)
        usersRecyclerView.adapter = usersRecyclerAdapter

        usersRecyclerAdapter.let {
            it.userDowngradeSelectedLiveData.observe(this, Observer { params ->
                managerViewModel.downgradeUser(params.userId).observe(this, Observer { state ->
                    when(state) {
                        UserStatusChangeState.UserDowngradeSucceed -> {
                            usersRecyclerAdapter.updateUserStatus(RegularUser, params.adapterPosition)
                        }
                        UserStatusChangeState.UserDowngradeFailed -> {
                            Toast.makeText(this, getString(R.string.downgrade_fail), Toast.LENGTH_SHORT).show()
                        }
                    }
                })
            })

            it.userUpgradeSelectedLiveData.observe(this, Observer { params ->
                managerViewModel.upgradeUser(params.userId).observe(this, Observer { state ->
                    when(state) {
                        UserStatusChangeState.UserUpgradeSucceed -> {
                            usersRecyclerAdapter.updateUserStatus(UserManager, params.adapterPosition)
                        }
                        UserStatusChangeState.UserUpgradeFailed -> {
                            Toast.makeText(this, getString(R.string.upgrade_fail), Toast.LENGTH_SHORT).show()
                        }
                    }
                })
            })
        }
    }
}