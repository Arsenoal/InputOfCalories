package com.example.inputofcalories.presentation.launch

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.inputofcalories.R
import com.example.inputofcalories.presentation.auth.AuthActivity
import com.example.inputofcalories.presentation.base.BaseActivity
import com.example.inputofcalories.presentation.navigation.ActivityNavigator
import org.koin.android.viewmodel.ext.android.viewModel

class LauncherActivity : BaseActivity() {

    private val launcherViewModel: LauncherViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_launcher)

        openAuth()
    }

    private fun openAuth() {
        ActivityNavigator.navigateAndFinishCurrent(this, AuthActivity::class.java)
    }
}
