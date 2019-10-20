package com.example.inputofcalories.presentation.regularflow

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import com.example.inputofcalories.R
import com.example.inputofcalories.common.logger.IOFLogger
import kotlinx.android.synthetic.main.activity_regular_user_home.*
import org.koin.android.ext.android.inject

class RegularUserHomeActivity : AppCompatActivity() {

    val mealsProviderViewModel: MealsProviderViewModel by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_regular_user_home)

        setupViewModel()

        setupClickListeners()
    }

    private fun setupViewModel() {
        mealsProviderViewModel.mealsLoadFailLiveData.observe(this, Observer {  })
        mealsProviderViewModel.mealsLoadSuccessLiveData.observe(this, Observer { list ->
            list.forEach { meal ->
                IOFLogger.d(TAG, meal.toString())
            }
        })
    }

    private fun setupClickListeners() {
        getUserMealsButton.setOnClickListener {
            mealsProviderViewModel.onGetMealsClicked()
        }
    }

    companion object {
        val TAG = RegularUserHomeActivity::class.java.name
    }
}
