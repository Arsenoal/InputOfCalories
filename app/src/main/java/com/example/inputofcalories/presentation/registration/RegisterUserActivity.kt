package com.example.inputofcalories.presentation.registration

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import com.example.inputofcalories.R
import com.example.inputofcalories.entity.User
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
        registerUserViewModel.userRegistrationFailLiveData.observe(this, Observer {  })
    }

    private fun setupClickListeners() {
        registerButton.setOnClickListener{
            val user = User(
                name = nameEditText.text.toString(),
                email = emailEditText.text.toString(),
                gender = genderEditText.text.toString()
            )

            registerUserViewModel.register(user)
        }

        signIn.setOnClickListener {
            //TODO navigate to sign in page
        }
    }
}
