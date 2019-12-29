package com.example.inputofcalories.presentation.auth.registration

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.lifecycle.Observer
import com.example.inputofcalories.R
import com.example.inputofcalories.entity.register.UserRegistrationParams
import com.example.inputofcalories.presentation.common.KeyboardManager
import com.example.inputofcalories.presentation.ProgressView
import com.example.inputofcalories.presentation.common.ToastManager
import com.example.inputofcalories.presentation.auth.signin.SignInActivity
import com.example.inputofcalories.presentation.common.SnackBarManager
import com.example.inputofcalories.presentation.navigation.ActivityNavigator
import kotlinx.android.synthetic.main.activity_register_user.*
import kotlinx.android.synthetic.main.progress_layout.*
import org.koin.android.viewmodel.ext.android.viewModel

class RegisterUserActivity : AppCompatActivity(), ProgressView {

    private val registerUserViewModel: RegisterUserViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_user)

        setupViewModels()

        setupClickListeners()
    }

    override fun onResume() {
        super.onResume()

        setupFocus()
    }

    override fun onPause() {
        super.onPause()

        KeyboardManager.hideKeyboard(this)
    }

    private fun setupViewModels() {
        registerUserViewModel.let {
            it.userRegistrationSuccessLiveData.observe(this, Observer {
                ActivityNavigator.navigateAndFinishCurrent(this, SignInActivity::class.java)
                hideProgress()
            })
            it.userRegistrationFailLiveData.observe(this, Observer {
                hideProgress()
                SnackBarManager.showSnackLong(view = parentView, text = resources.getString(R.string.registration_fail))
            })
            it.emailFormatInvalidLiveData.observe(this, Observer {
                hideProgress()
                SnackBarManager.showSnackLong(view = parentView, text = resources.getString(R.string.invalid_email_format))
            })
            it.passwordsMismatchLiveData.observe(this, Observer {
                hideProgress()
                SnackBarManager.showSnackLong(view = parentView, text = resources.getString(R.string.passwords_mismatch))
            })
            it.notAllFieldsAreFilledLiveData.observe(this, Observer {
                hideProgress()
                SnackBarManager.showSnackLong(view = parentView, text = resources.getString(R.string.fill_all_fields))
            })
        }
    }

    private fun setupClickListeners() {
        registerButton.setOnClickListener{
            KeyboardManager.hideKeyboard(this)
            showProgress()

            val userRegistrationParams = UserRegistrationParams(
                email = emailEditText.text.toString(),
                name = nameEditText.text.toString(),
                password = passwordEditText.text.toString(),
                repeatPassword = repeatPasswordEditText.text.toString()
            )

            registerUserViewModel.register(userRegistrationParams)
        }
    }

    private fun setupFocus() {
        nameEditText.text?.run {
            if(isEmpty()) {
                nameEditText.requestFocus()
                return
            }
        }

        emailEditText.text?.run {
            if (isEmpty()) {
                emailEditText.requestFocus()
                return
            }
        }

        passwordEditText.text?.run {
            if (isEmpty()) {
                passwordEditText.requestFocus()
                return
            }
        }

        repeatPasswordEditText.text?.run {
            if (isEmpty()) {
                repeatPasswordEditText.requestFocus()
                return
            }
        }
    }

    override fun showProgress() {
        progressContainer.visibility = VISIBLE
    }

    override fun hideProgress() {
        progressContainer.visibility = GONE
    }
}
