package com.example.inputofcalories.presentation.auth

import android.content.Context
import android.graphics.Color
import android.view.View
import androidx.lifecycle.Observer
import com.example.inputofcalories.R
import com.example.inputofcalories.common.extensions.empty
import com.example.inputofcalories.common.extensions.onTextChanged
import com.example.inputofcalories.entity.register.UserRegistrationParams
import com.example.inputofcalories.presentation.auth.entity.*
import com.example.inputofcalories.presentation.base.BaseFragment
import com.example.inputofcalories.presentation.common.ErrorView
import com.github.razir.progressbutton.attachTextChangeAnimator
import com.github.razir.progressbutton.bindProgressButton
import com.github.razir.progressbutton.hideProgress
import com.github.razir.progressbutton.showProgress
import kotlinx.android.synthetic.main.fragment_register.*

class RegisterFragment: BaseFragment() {

    private lateinit var authViewModel: AuthViewModel
    private var errorView: ErrorView? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)

        if(context is AuthActivity) authViewModel = context.authViewModel

        if(context is ErrorView) errorView = context
    }

    override fun getLayoutId() = R.layout.fragment_register

    override fun onLayoutReady(view: View) {
        setupUi()
    }

    private fun setupUi() {
        bindProgressButtons()

        setupClickListeners()

        setupTextWatchers()
    }

    private fun bindProgressButtons() {
        bindProgressButton(registerButton)

        registerButton.attachTextChangeAnimator()
    }

    private fun setupClickListeners() {
        registerButton.setOnClickListener {
            registerButton.showProgress {
                buttonTextRes = R.string.processing
                progressColor = Color.WHITE
            }

            val registrationParams = UserRegistrationParams(
                email = emailEditText.text.toString(),
                name = nameEditText.text.toString(),
                dailyCalories = String.empty(),
                password = passwordEditText.text.toString(),
                repeatPassword = repeatPasswordEditText.text.toString())

            registerButton.isClickable = false
            authViewModel.register(registrationParams).observe(viewLifecycleOwner, Observer { state ->
                registerButton.isClickable = true
                when(state) {
                    NotAllFieldsFilledForRegister -> {
                        registerButton.hideProgress(R.string.register)
                        errorView?.showErrorView(resources.getString(R.string.fill_all_fields))
                    }
                    InvalidRegisterEmailFormat -> {
                        registerButton.hideProgress(R.string.register)
                        errorView?.showErrorView(resources.getString(R.string.invalid_email_format))
                    }
                    PasswordsMismatch -> {
                        registerButton.hideProgress(R.string.register)
                        errorView?.showErrorView(resources.getString(R.string.passwords_mismatch))
                    }
                    RegisterSucceed -> {
                        registerButton.hideProgress(R.string.succeed)
                        openSignInFragment()
                    }
                    is RegisterFailed -> {
                        registerButton.hideProgress(R.string.register)
                        val text = if(state.message.text.isNotBlank()) state.message.text else resources.getString(R.string.registration_fail)

                        errorView?.showErrorView(text)
                    }
                }
            })
        }
    }

    private fun setupTextWatchers() {
        nameEditText.onTextChanged { errorView?.hideErrorView() }
        emailEditText.onTextChanged { errorView?.hideErrorView() }
        passwordEditText.onTextChanged { errorView?.hideErrorView() }
        repeatPasswordEditText.onTextChanged { errorView?.hideErrorView() }
    }

    private fun openSignInFragment() {
        activity?.let { activity -> if(activity is AuthActivity) activity.openSignInFragment() }
    }

    companion object {
        fun newInstance() = RegisterFragment()
    }
}