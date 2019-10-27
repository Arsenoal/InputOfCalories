package com.example.inputofcalories.presentation.auth.signin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import com.example.inputofcalories.R
import com.example.inputofcalories.entity.register.Admin
import com.example.inputofcalories.entity.register.RegularUser
import com.example.inputofcalories.entity.register.UserManager
import com.example.inputofcalories.entity.register.UserSignInParams
import com.example.inputofcalories.presentation.KeyboardManager
import com.example.inputofcalories.presentation.ProgressView
import com.example.inputofcalories.presentation.ToastManager
import com.example.inputofcalories.presentation.adminflow.home.AdminUserHomeActivity
import com.example.inputofcalories.presentation.commonextras.ExtraKeys.USER_ID_KEY
import com.example.inputofcalories.presentation.managerflow.home.ManagerUserHomeActivity
import com.example.inputofcalories.presentation.navigation.ActivityNavigator
import com.example.inputofcalories.presentation.regularflow.home.RegularUserHomeActivity
import kotlinx.android.synthetic.main.activity_sign_in.*
import kotlinx.android.synthetic.main.activity_sign_in.emailEditText
import kotlinx.android.synthetic.main.progress_layout.*
import org.koin.android.viewmodel.ext.android.viewModel

class SignInActivity : AppCompatActivity(), ProgressView {

    private val signInViewModel: SignInViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        setupViewModel()

        setupClickListeners()
    }

    private fun setupViewModel() {
        signInViewModel.let {
            it.singInSuccessLiveData.observe(this, Observer { user ->
                hideProgress()

                when(user.userParams.type) {
                    RegularUser -> {
                        ActivityNavigator.navigateAndFinishCurrent(this, RegularUserHomeActivity::class.java, USER_ID_KEY, user.id)
                    }
                    UserManager -> {
                        ActivityNavigator.navigateAndFinishCurrent(this, ManagerUserHomeActivity::class.java, USER_ID_KEY, user.id)
                    }
                    Admin -> {
                        ActivityNavigator.navigateAndFinishCurrent(this, AdminUserHomeActivity::class.java, USER_ID_KEY, user.id)
                    }
                }
            })

            it.singInFailLiveData.observe(this, Observer {
                hideProgress()
                ToastManager.showToastShort(this, resources.getString(R.string.sign_in_fail))
            })

            it.notAllFieldsAreFilledLiveData.observe(this, Observer {
                hideProgress()
                ToastManager.showToastShort(this, resources.getString(R.string.fill_all_fields))
            })

            it.emailFormatInvalidLiveData.observe(this, Observer {
                hideProgress()
                ToastManager.showToastShort(this, resources.getString(R.string.invalid_email_format))
            })
        }
    }

    private fun setupClickListeners() {
        signInButton.setOnClickListener {
            showProgress()
            KeyboardManager.hideKeyboard(this)

            val signInParams = UserSignInParams(
                email = emailEditText.text.toString(),
                password = passwordEditText.text.toString()
            )

            signInViewModel.signInClicked(signInParams)
        }
    }

    override fun showProgress() {
        progressContainer.visibility = View.VISIBLE
    }

    override fun hideProgress() {
        progressContainer.visibility = View.GONE
    }
}
