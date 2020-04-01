package com.example.inputofcalories.presentation.auth

import android.content.Context
import android.graphics.Color
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.inputofcalories.R
import com.example.inputofcalories.common.extensions.onTextChanged
import com.example.inputofcalories.entity.register.Admin
import com.example.inputofcalories.entity.register.RegularUser
import com.example.inputofcalories.entity.register.UserManager
import com.example.inputofcalories.entity.register.UserSignInParams
import com.example.inputofcalories.presentation.adminflow.home.AdminUserHomeActivity
import com.example.inputofcalories.presentation.auth.entity.InvalidSignInEmailFormat
import com.example.inputofcalories.presentation.auth.entity.NotAllFieldsForSignInFilled
import com.example.inputofcalories.presentation.auth.entity.SignInFailed
import com.example.inputofcalories.presentation.auth.entity.SignInSucceed
import com.example.inputofcalories.presentation.base.BaseFragment
import com.example.inputofcalories.presentation.common.ErrorView
import com.example.inputofcalories.presentation.commonextras.ExtraKeys.USER_ID_KEY
import com.example.inputofcalories.presentation.managerflow.home.ManagerUserHomeActivity
import com.example.inputofcalories.presentation.navigation.ActivityNavigator
import com.example.inputofcalories.presentation.regularflow.home.RegularUserHomeActivity
import com.github.razir.progressbutton.attachTextChangeAnimator
import com.github.razir.progressbutton.bindProgressButton
import com.github.razir.progressbutton.hideProgress
import com.github.razir.progressbutton.showProgress
import kotlinx.android.synthetic.main.fragment_sign_in.*

class SignInFragment: BaseFragment() {

    private lateinit var authViewModel: AuthViewModel
    private var errorView: ErrorView? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)

        if(context is AuthActivity) authViewModel = context.authViewModel

        if (context is ErrorView) errorView = context
    }

    override fun getLayoutId() = R.layout.fragment_sign_in

    override fun onLayoutReady(view: View) {
        setupUi()
    }

    private fun setupUi() {
        bindProgressButtons()

        setupClickListeners()

        setupWatchers()
    }

    private fun bindProgressButtons() {
        bindProgressButton(signInButton)

        signInButton.attachTextChangeAnimator()
    }

    private fun setupClickListeners() {
        signInButton.setOnClickListener {
            signInButton.showProgress {
                buttonTextRes = R.string.processing
                progressColor = Color.WHITE
            }

            val signInParams = UserSignInParams(
                email = emailEditText.text.toString(),
                password = passwordEditText.text.toString()
            )

            authViewModel.signIn(signInParams).observe(viewLifecycleOwner, Observer { state ->
                when(state) {
                    NotAllFieldsForSignInFilled -> {
                        signInButton.hideProgress(R.string.sign_in)

                        errorView?.showErrorView(resources.getString(R.string.fill_all_fields))
                    }
                    InvalidSignInEmailFormat -> {
                        signInButton.hideProgress(R.string.sign_in)

                        errorView?.showErrorView(resources.getString(R.string.invalid_email_format))
                    }
                    is SignInFailed -> {
                        signInButton.hideProgress(R.string.sign_in)
                        val text = if(state.message.text.isNotBlank()) state.message.text else resources.getString(R.string.sign_in_fail)

                        errorView?.showErrorView(text)
                    }
                    is SignInSucceed -> {
                        activity?.let { activity ->
                            signInButton.hideProgress(R.string.succeed)

                            val user = state.user
                            activity as AppCompatActivity

                            when(user.userParams.type) {
                                RegularUser -> { ActivityNavigator.navigateAndFinishCurrent(activity, RegularUserHomeActivity::class.java,
                                    USER_ID_KEY, user.id) }
                                UserManager -> { ActivityNavigator.navigateAndFinishCurrent(activity, ManagerUserHomeActivity::class.java,
                                    USER_ID_KEY, user.id) }
                                Admin -> { ActivityNavigator.navigateAndFinishCurrent(activity, AdminUserHomeActivity::class.java,
                                    USER_ID_KEY, user.id) }
                            }
                        }
                    }
                }
            })
        }
    }

    private fun setupWatchers() {
        emailEditText.onTextChanged { errorView?.hideErrorView() }
        passwordEditText.onTextChanged { errorView?.hideErrorView() }
    }

    companion object {
        fun newInstance() = SignInFragment()
    }
}