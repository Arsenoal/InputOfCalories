package com.example.inputofcalories.presentation.adminflow.home

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.inputofcalories.R
import com.example.inputofcalories.presentation.adminflow.home.model.entity.UserLoadState
import com.example.inputofcalories.presentation.adminflow.home.model.entity.UserStatusChangeState
import com.example.inputofcalories.presentation.common.ToastManager
import com.example.inputofcalories.presentation.adminflow.usermeals.UserMealsActivity
import com.example.inputofcalories.presentation.auth.AuthActivity
import com.example.inputofcalories.presentation.base.BaseActivity
import com.example.inputofcalories.presentation.commonextras.ExtraKeys.USER_ID_KEY
import com.example.inputofcalories.presentation.navigation.ActivityNavigator
import kotlinx.android.synthetic.main.activity_admin_user_home.*
import org.koin.android.viewmodel.ext.android.viewModel

class AdminUserHomeActivity: BaseActivity() {

    private val adminViewModel: AdminViewModel by viewModel()

    private val usersRecyclerAdapter = AllUsersRecyclerAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_user_home)

        setupToolbar()

        setupRecyclerView()

        loadUsers()
    }

    private fun setupToolbar() {
        setSupportActionBar(toolbar)
    }

    private fun loadUsers() {
        adminViewModel.loadUsers().observe(this, Observer { state ->
            when(state) {
                is UserLoadState.UsersLoadSucceed -> { usersRecyclerAdapter.setItems(state.users) }
                UserLoadState.NoUsersFound -> { noUsersToShowTextView.visibility = View.VISIBLE }
                UserLoadState.UsersLoadFailed -> { ToastManager.showToastShort(this, resources.getString(R.string.failed_to_load_users)) }
            }
        })
    }

    private fun setupRecyclerView() {
        usersRecyclerView.layoutManager = LinearLayoutManager(this)
        usersRecyclerView.adapter = usersRecyclerAdapter

        usersRecyclerAdapter.userSelectedLiveData.observe(this, Observer { userId ->
            ActivityNavigator.navigate(this, UserMealsActivity::class.java, USER_ID_KEY, userId)
        })

        usersRecyclerAdapter.userStatusChangeSelectedLiveData.observe(this, Observer { params ->
            adminViewModel.changeUserType(params.type, params.userId).observe(this, Observer { state ->
                when(state) {
                    UserStatusChangeState.UserStatusChangeSucceed -> { }
                    UserStatusChangeState.UserStatusChangeFailed -> { }
                }
            })
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_manager_user_home, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.logOut -> {
                ActivityNavigator.navigateAndClearStack(this, AuthActivity::class.java)
            }
        }

        return true
    }
}