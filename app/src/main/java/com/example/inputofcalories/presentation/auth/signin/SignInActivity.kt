package com.example.inputofcalories.presentation.auth.signin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import com.example.inputofcalories.R
import com.example.inputofcalories.entity.register.UserSignInParams
import com.example.inputofcalories.presentation.ToastManager
import com.example.inputofcalories.presentation.navigation.ActivityNavigator
import com.example.inputofcalories.presentation.regularflow.home.RegularUserHomeActivity
import kotlinx.android.synthetic.main.activity_sign_in.*
import kotlinx.android.synthetic.main.activity_sign_in.emailEditText
import org.koin.android.ext.android.inject

class SignInActivity : AppCompatActivity() {

    val signInViewModel: SignInViewModel by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        setupViewModel()

        setupClickListeners()
    }

    private fun setupViewModel() {
        signInViewModel.singInSuccessLiveData.observe(this, Observer {
            ActivityNavigator.navigateAndFinishCurrent(this, RegularUserHomeActivity::class.java)
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
