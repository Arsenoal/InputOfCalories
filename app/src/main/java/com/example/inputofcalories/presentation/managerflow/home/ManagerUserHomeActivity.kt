package com.example.inputofcalories.presentation.managerflow.home

import android.os.Bundle
import android.view.View.VISIBLE
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.inputofcalories.R
import com.example.inputofcalories.presentation.ToastManager
import kotlinx.android.synthetic.main.activity_manager_user_home.*
import org.koin.android.viewmodel.ext.android.viewModel

class ManagerUserHomeActivity: AppCompatActivity() {

    private val regularUsersProviderViewModel: RegularUsersProviderViewModel by viewModel()

    private val usersRecyclerAdapter = UsersRecyclerAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_manager_user_home)

        setupUsersRecyclerView()

        setupViewModel()
    }

    private fun setupViewModel() {
        regularUsersProviderViewModel.usersLoadFailLiveData.observe(this, Observer { message ->
            ToastManager.showToastShort(this, message.message)
        })

        regularUsersProviderViewModel.usersLoadSuccessLiveData.observe(this, Observer { users ->
            usersRecyclerAdapter.setItems(users)
        })

        regularUsersProviderViewModel.noUsersFoundLiveData.observe(this, Observer {
            noUsersToShowTextView.visibility = VISIBLE
        })

        regularUsersProviderViewModel.getUsers()
    }

    private fun setupUsersRecyclerView() {
        usersRecyclerView.layoutManager = LinearLayoutManager(this)
        usersRecyclerView.adapter = usersRecyclerAdapter
    }
}