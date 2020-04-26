package com.example.inputofcalories.presentation.auth

import android.os.Bundle

import com.example.inputofcalories.R
import com.example.inputofcalories.common.extensions.onItemSelected
import com.example.inputofcalories.presentation.base.BaseActivity
import com.example.inputofcalories.presentation.navigation.FragmentNavigator
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

    private fun openSignInFragment() {
        FragmentNavigator.openOrReplace(this, SignInFragment.newInstance(), authFrameView.id, SignInFragment::class.java.name)
    }

    private fun openRegisterFragment() {
        FragmentNavigator.openOrReplace(this, RegisterFragment.newInstance(), authFrameView.id, RegisterFragment::class.java.name)
    }

    override fun onBackPressed() {
        finish()
    }
}