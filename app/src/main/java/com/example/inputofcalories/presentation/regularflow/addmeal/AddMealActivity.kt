package com.example.inputofcalories.presentation.regularflow.addmeal

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.inputofcalories.R
import com.example.inputofcalories.entity.presentation.regular.*
import com.example.inputofcalories.presentation.ToastManager
import com.example.inputofcalories.presentation.navigation.ActivityNavigator
import kotlinx.android.synthetic.main.activity_add_meal.*
import kotlinx.android.synthetic.main.activity_add_meal.mealDateRadioGroup
import org.koin.android.viewmodel.ext.android.viewModel
import java.util.*

class AddMealActivity: AppCompatActivity() {

    private val addMealViewModel: AddMealViewModel by viewModel()

    var mealTime: MealTimeParams = LunchTime

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_meal)

        setupViewModel()

        setupMealTimePickerListener()

        setupClickListeners()
    }

    private fun setupViewModel() {
        addMealViewModel.let {
            it.addMealFailLiveData.observe(this, Observer {
                ToastManager.showToastShort(this, resources.getString(R.string.meal_add_failed))
            })

            it.addMealSuccessLiveData.observe(this, Observer {
                ToastManager.showToastShort(this, resources.getString(R.string.meal_successfully_added))
                ActivityNavigator.navigateBack(this)
            })

            it.mealParamsAreInvalidLiveData.observe(this, Observer {
                ToastManager.showToastShort(this, resources.getString(R.string.meal_params_are_invalid))
            })
        }
    }

    private fun setupClickListeners() {
        addMealButton.setOnClickListener {
            val params = MealParams(
                text = mealTextEditText.text.toString(),
                calories = mealCaloriesEditText.text.toString(),
                weight = mealWeightEditText.text.toString()
            )

            val year = Calendar.getInstance().get(Calendar.YEAR).toString()
            val month = Calendar.getInstance().get(Calendar.MONTH).toString()
            val dayOfMonth = Calendar.getInstance().get(Calendar.DAY_OF_MONTH).toString()

            val filterParams = MealFilterParams(
                MealDateParams(year, month, dayOfMonth),
                mealTime
            )

            addMealViewModel.addMealClicked(params, filterParams)
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