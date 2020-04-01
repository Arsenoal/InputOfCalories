package com.example.inputofcalories.presentation.auth

import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE

import com.example.inputofcalories.R
import com.example.inputofcalories.presentation.base.BaseActivity
import com.example.inputofcalories.presentation.common.ErrorView
import com.example.inputofcalories.presentation.navigation.FragmentNavigator
import kotlinx.android.synthetic.main.activity_auth.*
import org.koin.android.viewmodel.ext.android.viewModel

class AuthActivity : BaseActivity(), ErrorView {

    val authViewModel: AuthViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)

        setupClickListeners()

        openSignInFragment()
    }

    private fun setupClickListeners() {
        signInButton.setOnClickListener {
            hideErrorView()
            openSignInFragment()
        }

        signUpButton.setOnClickListener {
            hideErrorView()
            openRegisterFragment()
        }
    }

    override fun showErrorView(text: String) {
        errorMessageTextView.text = text
        errorView.visibility = VISIBLE
    }

    override fun hideErrorView() {
        errorView.visibility = GONE
    }

    fun openSignInFragment() {
        FragmentNavigator.openFragment(this, SignInFragment.newInstance(), authFrameView.id, SignInFragment::class.java.name)
    }

    fun openRegisterFragment() {
        FragmentNavigator.openFragment(this, RegisterFragment.newInstance(), authFrameView.id, RegisterFragment::class.java.name)
    }
}