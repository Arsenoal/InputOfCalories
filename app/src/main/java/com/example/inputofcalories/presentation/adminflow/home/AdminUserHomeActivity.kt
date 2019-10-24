package com.example.inputofcalories.presentation.adminflow.home

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.inputofcalories.R
import com.example.inputofcalories.presentation.ToastManager
import kotlinx.android.synthetic.main.activity_manager_user_home.*
import org.koin.android.viewmodel.ext.android.viewModel

class AdminUserHomeActivity: AppCompatActivity() {

    private val allUsersProviderViewModel: AllUsersProviderViewModel by viewModel()

    private val adminUserStatusManipulatorViewModel: AdminUserStatusManipulatorViewModel by viewModel()

    private val usersRecyclerAdapter = AllUsersRecyclerAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_user_home)

        setupStatusManipulatorViewModel()

        setupRecyclerView()

        setupUsersProviderViewModel()
    }

    private fun setupStatusManipulatorViewModel() {
        adminUserStatusManipulatorViewModel.let {
            it.userUpgradeSucceedLiveData.observe(this, Observer {
                allUsersProviderViewModel.getUsers()
                ToastManager.showToastShort(this, "upgraded")
            })
            it.userUpgradeFailLiveData.observe(this, Observer { message ->
                ToastManager.showToastShort(this, message.message)
            })

            it.userDowngradeSucceedLiveData.observe(this, Observer {
                allUsersProviderViewModel.getUsers()
                ToastManager.showToastShort(this, "downgraded")
            })
            it.userDowngradeFailLiveData.observe(this, Observer { message ->
                ToastManager.showToastShort(this, message.message)
            })
        }
    }

    private fun setupUsersProviderViewModel() {
        allUsersProviderViewModel.let {
            it.usersLoadFailLiveData.observe(this, Observer { message ->
                ToastManager.showToastShort(this, message.message)
            })

            it.usersLoadSuccessLiveData.observe(this, Observer { users ->
                usersRecyclerAdapter.setItems(users)
            })

            it.noUsersFoundLiveData.observe(this, Observer {
                noUsersToShowTextView.visibility = View.VISIBLE
            })

            it.getUsers()
        }
    }

    private fun setupRecyclerView() {
        usersRecyclerView.layoutManager = LinearLayoutManager(this)
        usersRecyclerView.adapter = usersRecyclerAdapter

        usersRecyclerAdapter.run {
            userDowngradeSelectedLiveData.observe(this@AdminUserHomeActivity, Observer { user ->
                adminUserStatusManipulatorViewModel.downgradeUserClicked(user)
            })

            userUpgradeSelectedLiveData.observe(this@AdminUserHomeActivity, Observer { user ->
                adminUserStatusManipulatorViewModel.upgradeUserClicked(user)
            })
        }
    }
}