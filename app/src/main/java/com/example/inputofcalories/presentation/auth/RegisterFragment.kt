package com.example.inputofcalories.presentation.auth

import android.content.Context
import android.graphics.Color
import android.view.View
import android.view.WindowManager
import androidx.lifecycle.Observer
import com.example.inputofcalories.R
import com.example.inputofcalories.common.extensions.empty
import com.example.inputofcalories.common.extensions.onTextChanged
import com.example.inputofcalories.entity.register.UserRegistrationParams
import com.example.inputofcalories.presentation.auth.entity.*
import com.example.inputofcalories.presentation.base.BaseFragment
import com.example.inputofcalories.presentation.common.ErrorView
import com.example.inputofcalories.presentation.common.KeyboardManager
import com.github.razir.progressbutton.attachTextChangeAnimator
import com.github.razir.progressbutton.bindProgressButton
import com.github.razir.progressbutton.hideProgress
import com.github.razir.progressbutton.showProgress
import kotlinx.android.synthetic.main.activity_auth.*
import kotlinx.android.synthetic.main.fragment_register.*
import kotlinx.android.synthetic.main.fragment_register.emailEditText
import kotlinx.android.synthetic.main.fragment_register.errorTextView
import kotlinx.android.synthetic.main.fragment_register.passwordEditText

class RegisterFragment: BaseFragment(), ErrorView {

    private lateinit var authViewModel: AuthViewModel

    override fun onAttach(context: Context) {
        super.onAttach(context)

        if(context is AuthActivity) authViewModel = context.authViewModel

    }

    override fun onResume() {
        super.onResume()
        hideErrorView()
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
            registerButton.isClickable = false

            activity?.run { KeyboardManager.hideKeyboard(this) }

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

                        showErrorView(resources.getString(R.string.fill_all_fields))
                    }
                    InvalidRegisterEmailFormat -> {
                        registerButton.hideProgress(R.string.register)

                        emailEditText.error = resources.getString(R.string.invalid_email_format)
                    }
                    PasswordsMismatch -> {
                        registerButton.hideProgress(R.string.register)

                        repeatPasswordEditText.error = resources.getString(R.string.passwords_mismatch)
                    }
                    RegisterSucceed -> {
                        registerButton.hideProgress(R.string.succeed)

                        (activity as AuthActivity).run { tabs.getTabAt(SIGN_IN_TAB)?.select() }
                    }
                    is RegisterFailed -> {
                        registerButton.hideProgress(R.string.register)
                        val text = if(state.message.text.isNotBlank()) state.message.text else resources.getString(R.string.registration_fail)

                        showErrorView(text)
                    }
                }
            })
        }
    }

    private fun setupTextWatchers() {
        nameEditText.onTextChanged { hideErrorView() }
        emailEditText.onTextChanged { hideErrorView() }
        passwordEditText.onTextChanged { hideErrorView() }
        repeatPasswordEditText.onTextChanged { hideErrorView() }
    }

    override fun softInputModeFlags() = listOf(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)

    override fun showErrorView(text: String) {
        errorTextView.visibility = View.VISIBLE
        errorTextView.text = text
    }

    override fun hideErrorView() {
        errorTextView.visibility = View.GONE
    }

    companion object {
        fun newInstance() = RegisterFragment()
    }
}