package com.example.inputofcalories.presentation.launch

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.inputofcalories.R
import com.example.inputofcalories.presentation.navigation.ActivityNavigator
import com.example.inputofcalories.presentation.auth.registration.RegisterUserActivity

class LauncherActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        openRegistration()
    }

    private fun openRegistration() {

        ActivityNavigator.navigateAndFinishCurrent(this, RegisterUserActivity::class.java)
    }
}
