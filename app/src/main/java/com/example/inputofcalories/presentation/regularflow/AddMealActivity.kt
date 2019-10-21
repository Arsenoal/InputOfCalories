package com.example.inputofcalories.presentation.regularflow

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.inputofcalories.R
import com.example.inputofcalories.entity.MealParams
import com.example.inputofcalories.presentation.ToastManager
import com.example.inputofcalories.presentation.navigation.ActivityNavigator
import kotlinx.android.synthetic.main.fragment_add_meal.*
import org.koin.android.ext.android.inject

class AddMealActivity: AppCompatActivity() {

    val addMealViewModel: AddMealViewModel by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_add_meal)

        setupViewModel()

        setupClickListeners()
    }

    private fun setupViewModel() {
        addMealViewModel.addMealFailLiveData.observe(this, Observer {
            ToastManager.showToastShort(this, "meal add failed")
        })

        addMealViewModel.addMealSuccessLiveData.observe(this, Observer {
            ToastManager.showToastShort(this, "meal successfully added")
            ActivityNavigator.navigateBack(this)
        })
    }

    private fun setupClickListeners() {
        addMealButton.setOnClickListener {
            val mealParams = MealParams(
                text = mealTextEditText.text.toString(),
                calories = mealCaloriesEditText.text.toString(),
                weight = mealWeightEditText.text.toString()
            )

            addMealViewModel.addMealClicked(mealParams)
        }
    }
}