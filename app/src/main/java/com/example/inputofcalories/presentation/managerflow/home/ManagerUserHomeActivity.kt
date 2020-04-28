package com.example.inputofcalories.presentation.managerflow.home

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.inputofcalories.R
import com.example.inputofcalories.presentation.auth.AuthActivity
import com.example.inputofcalories.presentation.base.BaseActivity
import com.example.inputofcalories.presentation.managerflow.home.ManagerFlowObserverFactory.ObserverKey
import com.example.inputofcalories.presentation.navigation.ActivityNavigator
import kotlinx.android.synthetic.main.activity_manager_user_home.*
import kotlinx.android.synthetic.main.activity_manager_user_home.toolbar
import kotlinx.android.synthetic.main.activity_regular_user_home.*
import org.koin.android.viewmodel.ext.android.viewModel

class ManagerUserHomeActivity: BaseActivity() {

    private val managerViewModel: ManagerViewModel by viewModel()

    private lateinit var managerFlowObserverFactory: ManagerFlowObserverFactory

    val usersRecyclerAdapter = UsersRecyclerAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_manager_user_home)

        setupToolbar()

        initObserverFactory()

        loadUsers()

        setupUsersRecyclerView()
    }

    private fun setupToolbar() {
        setSupportActionBar(toolbar)
    }

    private fun initObserverFactory() {
        managerFlowObserverFactory = ManagerFlowObserverFactory(this)
    }

    private fun loadUsers() {
        managerViewModel.loadUsers().observe(this, managerFlowObserverFactory.get(ObserverKey.GetUsersObserver))
    }

    private fun setupUsersRecyclerView() {
        usersRecyclerView.layoutManager = LinearLayoutManager(this)
        usersRecyclerView.adapter = usersRecyclerAdapter

        usersRecyclerAdapter.let {
            it.userDowngradeSelectedLiveData.observe(this, Observer { params ->
                managerViewModel.downgradeUser(params.userId).observe(this, managerFlowObserverFactory.get(ObserverKey.DowngradeUserObserver(params)))
            })

            it.userUpgradeSelectedLiveData.observe(this, Observer { params ->
                managerViewModel.upgradeUser(params.userId).observe(this, managerFlowObserverFactory.get(ObserverKey.UpgradeUserObserver(params)))
            })
        }
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