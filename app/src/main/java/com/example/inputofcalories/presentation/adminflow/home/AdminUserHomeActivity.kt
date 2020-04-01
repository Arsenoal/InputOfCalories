package com.example.inputofcalories.presentation.adminflow.home

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.inputofcalories.R
import com.example.inputofcalories.presentation.common.ToastManager
import com.example.inputofcalories.presentation.adminflow.usermeals.UserMealsActivity
import com.example.inputofcalories.presentation.base.BaseActivity
import com.example.inputofcalories.presentation.commonextras.ExtraKeys.USER_ID_KEY
import com.example.inputofcalories.presentation.navigation.ActivityNavigator
import kotlinx.android.synthetic.main.activity_manager_user_home.*
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class AdminUserHomeActivity: BaseActivity() {

    private lateinit var allUsersProviderViewModel: AllUsersProviderViewModel

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
                ToastManager.showToastShort(this, resources.getString(R.string.upgraded))
            })
            it.userUpgradeFailLiveData.observe(this, Observer {
                ToastManager.showToastShort(this, resources.getString(R.string.update_failed))
            })

            it.userDowngradeSucceedLiveData.observe(this, Observer {
                allUsersProviderViewModel.getUsers()
                ToastManager.showToastShort(this, resources.getString(R.string.downgraded))
            })
            it.userDowngradeFailLiveData.observe(this, Observer {
                ToastManager.showToastShort(this, resources.getString(R.string.downgrade_fail))
            })
        }
    }

    private fun setupUsersProviderViewModel() {
        val allUsersProviderViewModel: AllUsersProviderViewModel by viewModel{ parametersOf(getUserIdExtra()) }
        this.allUsersProviderViewModel = allUsersProviderViewModel

        this.allUsersProviderViewModel.let {
            it.usersLoadFailLiveData.observe(this, Observer {
                ToastManager.showToastShort(this, resources.getString(R.string.failed_to_load_users))
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

        usersRecyclerAdapter.let {
            it.userSelectedLiveData.observe(this, Observer { userId ->
                ActivityNavigator.navigate(this, UserMealsActivity::class.java, USER_ID_KEY, userId)
            })

            it.userDowngradeSelectedLiveData.observe(this, Observer { user ->
                adminUserStatusManipulatorViewModel.downgradeUserClicked(user)
            })

            it.userUpgradeSelectedLiveData.observe(this, Observer { user ->
                adminUserStatusManipulatorViewModel.upgradeUserClicked(user)
            })
        }
    }

    private fun getUserIdExtra() = intent.getStringExtra(USER_ID_KEY)
}