package com.example.inputofcalories.presentation.regularflow.addmeal

import android.os.Bundle
import androidx.lifecycle.Observer
import com.example.inputofcalories.R
import com.example.inputofcalories.entity.presentation.regular.*
import com.example.inputofcalories.presentation.base.BaseActivity
import com.example.inputofcalories.presentation.common.ToastManager
import com.example.inputofcalories.presentation.navigation.ActivityNavigator
import com.example.inputofcalories.presentation.regularflow.model.entity.AddMealState
import kotlinx.android.synthetic.main.add_meal_activity.*
import kotlinx.android.synthetic.main.add_meal_activity.mealDateRadioGroup
import org.koin.android.viewmodel.ext.android.viewModel
import java.util.*

class AddMealActivity: BaseActivity() {

    private val addMealViewModel: AddMealViewModel by viewModel()

    private var mealTime: MealTimeParams = LunchTime

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_meal_activity)

        setupMealTimePickerListener()

        setupClickListeners()
    }

    private fun setupClickListeners() {
        addMealButton.setOnClickListener {
            val params = MealParams(
                text = mealTextEditText.text.toString(),
                calories = mealCaloriesEditText.text.toString(),
                weight = mealWeightEditText.text.toString())

            val year = Calendar.getInstance().get(Calendar.YEAR).toString()
            val month = Calendar.getInstance().get(Calendar.MONTH).toString()
            val dayOfMonth = Calendar.getInstance().get(Calendar.DAY_OF_MONTH).toString()

            val filterParams = MealFilterParams(
                MealDateParams(year, month, dayOfMonth),
                mealTime)

            addMealViewModel.addMeal(params, filterParams).observe(this, Observer { state ->
                when(state) {
                    AddMealState.AddMealSucceed -> {
                        ToastManager.showToastShort(this, resources.getString(R.string.meal_successfully_added))
                        ActivityNavigator.navigateBack(this)
                    }
                    AddMealState.AddMealFailed -> {
                        ToastManager.showToastShort(this, resources.getString(R.string.meal_add_failed))
                    }
                }
            })
        }
    }

    private fun setupMealTimePickerListener() {
        mealDateRadioGroup.setOnCheckedChangeListener { _, id ->
            when(id) {
                R.id.breakfastButton -> { mealTime = BreakfastTime }
                R.id.lunchButton -> { mealTime = LunchTime }
                R.id.dinnerButton -> { mealTime = DinnerTime }
                R.id.snackButton -> { mealTime = SnackTime }
            }
        }
    }
}