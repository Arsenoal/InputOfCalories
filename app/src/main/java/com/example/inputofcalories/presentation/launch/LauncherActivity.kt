package com.example.inputofcalories.presentation.launch

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.inputofcalories.R
import com.example.inputofcalories.presentation.navigation.ActivityNavigator
import com.example.inputofcalories.presentation.auth.registration.RegisterUserActivity
import org.koin.android.viewmodel.ext.android.viewModel

class LauncherActivity : AppCompatActivity() {

    private val invalidateLocalDataViewModel: InvalidateLocalDataViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_launcher)

        setupViewModel()

        clearLocalData()
    }

    private fun setupViewModel() {
        invalidateLocalDataViewModel.let {
            it.dataClearSucceedLiveData.observe(this, Observer {
                openRegistration()
            })
        }
    }

    private fun clearLocalData() {
        invalidateLocalDataViewModel.clearLocalData()
    }

    private fun openRegistration() {
        ActivityNavigator.navigateAndFinishCurrent(this, RegisterUserActivity::class.java)
    }
}
