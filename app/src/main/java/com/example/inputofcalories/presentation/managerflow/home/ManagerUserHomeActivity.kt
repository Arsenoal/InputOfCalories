package com.example.inputofcalories.presentation.managerflow.home

import android.os.Bundle
import android.view.View.VISIBLE
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.inputofcalories.R
import com.example.inputofcalories.presentation.ToastManager
import com.example.inputofcalories.presentation.regularflow.home.USER_ID_KEY
import kotlinx.android.synthetic.main.activity_manager_user_home.*
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class ManagerUserHomeActivity: AppCompatActivity() {

    private lateinit var usersProviderViewModel: UsersProviderViewModel

    private val userStatusManipulatorViewModel: ManagerUserStatusManipulatorViewModel by viewModel()

    private val usersRecyclerAdapter = UsersRecyclerAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_manager_user_home)

        setupUsersProviderViewModel()

        setupStatusManipulatorViewModel()

        setupUsersRecyclerView()
    }

    private fun setupStatusManipulatorViewModel() {
        userStatusManipulatorViewModel.let {
            it.userUpgradeSucceedLiveData.observe(this, Observer {
                usersProviderViewModel.getUsers()
                ToastManager.showToastShort(this, "upgraded")
            })
            it.userUpgradeFailLiveData.observe(this, Observer { message ->
                ToastManager.showToastShort(this, message.message)
            })

            it.userDowngradeSucceedLiveData.observe(this, Observer {
                usersProviderViewModel.getUsers()
                ToastManager.showToastShort(this, "downgraded")
            })
            it.userDowngradeFailLiveData.observe(this, Observer { message ->
                ToastManager.showToastShort(this, message.message)
            })
        }
    }

    private fun setupUsersProviderViewModel() {
        val usersProviderViewModel: UsersProviderViewModel by viewModel{ parametersOf(getUserIdExtra()) }
        this.usersProviderViewModel = usersProviderViewModel

        this.usersProviderViewModel.let {
            it.usersLoadFailLiveData.observe(this, Observer { message ->
                ToastManager.showToastShort(this, message.message)
            })

            it.usersLoadSuccessLiveData.observe(this, Observer { users ->
                usersRecyclerAdapter.setItems(users)
            })

            it.noUsersFoundLiveData.observe(this, Observer {
                noUsersToShowTextView.visibility = VISIBLE
            })

            it.getUsers()
        }
    }

    private fun setupUsersRecyclerView() {
        usersRecyclerView.layoutManager = LinearLayoutManager(this)
        usersRecyclerView.adapter = usersRecyclerAdapter

        usersRecyclerAdapter.run {
            userDowngradeSelectedLiveData.observe(this@ManagerUserHomeActivity, Observer { user ->
                userStatusManipulatorViewModel.downgradeUserClicked(user)
            })

            userUpgradeSelectedLiveData.observe(this@ManagerUserHomeActivity, Observer { user ->
                userStatusManipulatorViewModel.upgradeUserClicked(user)
            })
        }
    }

    private fun getUserIdExtra() = intent.getStringExtra(USER_ID_KEY)
}