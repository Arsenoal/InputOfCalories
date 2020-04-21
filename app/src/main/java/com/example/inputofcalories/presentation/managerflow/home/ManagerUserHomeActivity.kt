package com.example.inputofcalories.presentation.managerflow.home

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.inputofcalories.R
import com.example.inputofcalories.presentation.base.BaseActivity
import com.example.inputofcalories.presentation.managerflow.home.ManagerFlowObserverFactory.ObserverKey
import kotlinx.android.synthetic.main.activity_manager_user_home.*
import org.koin.android.viewmodel.ext.android.viewModel

class ManagerUserHomeActivity: BaseActivity() {

    private val managerViewModel: ManagerViewModel by viewModel()

    private lateinit var managerFlowObserverFactory: ManagerFlowObserverFactory

    val usersRecyclerAdapter = UsersRecyclerAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_manager_user_home)

        initObserverFactory()

        loadUsers()

        setupUsersRecyclerView()
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
}