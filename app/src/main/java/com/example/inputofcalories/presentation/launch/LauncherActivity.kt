package com.example.inputofcalories.presentation.launch

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.inputofcalories.R
import org.koin.android.ext.android.inject

class LauncherActivity : AppCompatActivity() {

    private val launcherViewModel: LauncherViewModel by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupViewModel()
    }

    private fun setupViewModel() {
        launcherViewModel.statusUserNotRegisteredLiveData.observe(this, Observer {  })
        launcherViewModel.statusRegularUserLiveData.observe(this, Observer {  })
        launcherViewModel.statusUserManagerLiveData.observe(this, Observer {  })
        launcherViewModel.statusAdminLiveData.observe(this, Observer {  })
    }
}
