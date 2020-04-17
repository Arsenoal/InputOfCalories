package com.example.inputofcalories.presentation.auth

import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE

import com.example.inputofcalories.R
import com.example.inputofcalories.common.extensions.onItemSelected
import com.example.inputofcalories.presentation.base.BaseActivity
import com.example.inputofcalories.presentation.common.ErrorView
import com.example.inputofcalories.presentation.navigation.FragmentNavigator
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.activity_auth.*
import org.koin.android.viewmodel.ext.android.viewModel

const val SIGN_IN_TAB = 0
const val SIGN_UP_TAB = 1

class AuthActivity : BaseActivity() {

    val authViewModel: AuthViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)

        setupClickListeners()

        openSignInFragment()
    }

    private fun setupClickListeners() {
        tabs.onItemSelected {
            when(it) {
                SIGN_IN_TAB -> {
                    openSignInFragment()
                }
                SIGN_UP_TAB -> {
                    openRegisterFragment()
                }
            }
        }
    }

    fun openSignInFragment() {
        FragmentNavigator.openFragment(this, SignInFragment.newInstance(), authFrameView.id, SignInFragment::class.java.name)
    }

    fun openRegisterFragment() {
        FragmentNavigator.openFragment(this, RegisterFragment.newInstance(), authFrameView.id, RegisterFragment::class.java.name)
    }
}