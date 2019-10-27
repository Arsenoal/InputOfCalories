package com.example.inputofcalories.presentation.auth.signin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import com.example.inputofcalories.R
import com.example.inputofcalories.entity.register.Admin
import com.example.inputofcalories.entity.register.RegularUser
import com.example.inputofcalories.entity.register.UserManager
import com.example.inputofcalories.entity.register.UserSignInParams
import com.example.inputofcalories.presentation.ToastManager
import com.example.inputofcalories.presentation.adminflow.home.AdminUserHomeActivity
import com.example.inputofcalories.presentation.managerflow.home.ManagerUserHomeActivity
import com.example.inputofcalories.presentation.navigation.ActivityNavigator
import com.example.inputofcalories.presentation.regularflow.home.RegularUserHomeActivity
import com.example.inputofcalories.presentation.regularflow.home.USER_ID_KEY
import kotlinx.android.synthetic.main.activity_sign_in.*
import kotlinx.android.synthetic.main.activity_sign_in.emailEditText
import org.koin.android.viewmodel.ext.android.viewModel

class SignInActivity : AppCompatActivity() {

    private val signInViewModel: SignInViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        setupViewModel()

        setupClickListeners()
    }

    private fun setupViewModel() {
        signInViewModel.singInSuccessLiveData.observe(this, Observer { user ->
            user.run {
                when(userParams.type) {
                    RegularUser -> {
                        ActivityNavigator.navigateAndFinishCurrent(this@SignInActivity, RegularUserHomeActivity::class.java, USER_ID_KEY, id)
                    }
                    UserManager -> {
                        ActivityNavigator.navigateAndFinishCurrent(this@SignInActivity, ManagerUserHomeActivity::class.java, USER_ID_KEY, id)
                    }
                    Admin -> {
                        ActivityNavigator.navigateAndFinishCurrent(this@SignInActivity, AdminUserHomeActivity::class.java)
                    }
                }
            }

        })

        signInViewModel.singInFailLiveData.observe(this, Observer {
            ToastManager.showToastShort(this, it.message)
        })
    }

    private fun setupClickListeners() {
        signInButton.setOnClickListener {
            val signInParams = UserSignInParams(
                email = emailEditText.text.toString(),
                password = passwordEditText.text.toString()
            )

            signInViewModel.signInClicked(signInParams)
        }
    }
}
