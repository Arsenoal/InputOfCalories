package com.example.inputofcalories.presentation.auth.registration

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import com.example.inputofcalories.R
import com.example.inputofcalories.entity.register.UserRegistrationParams
import com.example.inputofcalories.presentation.ToastManager
import com.example.inputofcalories.presentation.auth.signin.SignInActivity
import com.example.inputofcalories.presentation.navigation.ActivityNavigator
import kotlinx.android.synthetic.main.activity_register_user.*
import org.koin.android.viewmodel.ext.android.viewModel

class RegisterUserActivity : AppCompatActivity() {

    private val registerUserViewModel: RegisterUserViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_user)

        setupViewModels()

        setupClickListeners()
    }

    private fun setupViewModels() {
        registerUserViewModel.userRegistrationSuccessLiveData.observe(this, Observer {  })
        registerUserViewModel.userRegistrationFailLiveData.observe(this, Observer {
            ToastManager.showToastShort(this, it.message)
        })
    }

    private fun setupClickListeners() {
        registerButton.setOnClickListener{
            val userRegistrationParams = UserRegistrationParams(
                email = emailEditText.text.toString(),
                name = nameEditText.text.toString(),
                password = passwordEditText.text.toString(),
                repeatPassword = repeatPasswordEditText.text.toString()
            )

            registerUserViewModel.register(userRegistrationParams)
        }

        signIn.setOnClickListener {
            ActivityNavigator.navigateAndFinishCurrent(this, SignInActivity::class.java)
        }
    }
}
