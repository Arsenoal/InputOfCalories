package com.example.inputofcalories.presentation.registration

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import com.example.inputofcalories.R
import com.example.inputofcalories.entity.register.UserParams
import com.example.inputofcalories.entity.register.UserRegistrationParams
import com.example.inputofcalories.entity.register.UserUpdateParams
import com.example.inputofcalories.presentation.ToastManager
import kotlinx.android.synthetic.main.activity_register_user.*
import org.koin.android.ext.android.inject

class RegisterUserActivity : AppCompatActivity() {

    private val registerUserViewModel: RegisterUserViewModel by inject()

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
            //TODO navigate to sign in page
        }
    }
}
