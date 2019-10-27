package com.example.inputofcalories.presentation.regularflow.editmeal

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.inputofcalories.R
import com.example.inputofcalories.entity.presentation.regular.*
import com.example.inputofcalories.presentation.ToastManager
import com.example.inputofcalories.presentation.navigation.ActivityNavigator
import com.example.inputofcalories.presentation.regularflow.home.RegularUserHomeActivity
import com.example.inputofcalories.presentation.regularflow.model.MealSerializable
import com.example.inputofcalories.presentation.regularflow.viewmeal.MEAL_EXTRA
import kotlinx.android.synthetic.main.activity_edit_meal.*
import org.koin.android.viewmodel.ext.android.viewModel

class EditMealActivity: AppCompatActivity() {

    private val editMealViewModel: EditMealViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_meal)

        updateUi(getMealSerializableExtra())

        setupClickListeners()

        setupViewModel()
    }

    private fun updateUi(mealSerializable: MealSerializable) {
        mealTextEditText.setText(mealSerializable.text)
        mealCaloriesEditText.setText(mealSerializable.calories)
        mealWeightEditText.setText(mealSerializable.weight)
    }

    private fun setupViewModel() {
        editMealViewModel.mealEditFailedLiveData.observe(this, Observer {
            ToastManager.showToastShort(this, it.message)
        })

        editMealViewModel.mealEditSucceededLiveData.observe(this, Observer {
            ToastManager.showToastShort(this, "meal edit succeeded")
            ActivityNavigator.navigateAndFinishCurrent(this, RegularUserHomeActivity::class.java)
        })
    }

    private fun setupClickListeners() {
        editMealButton.setOnClickListener {
            getMealSerializableExtra().run {
                val meal = Meal(
                    id = id,
                    params = MealParams(
                        text = text,
                        calories = calories,
                        weight = weight),
                    filterParams = MealFilterParams(
                        date = MealDateParams(
                            year = year,
                            month = month,
                            dayOfMonth = dayOfMonth),
                        time = LunchTime()
                    )
                )

                editMealViewModel.editClicked(meal)
            }
        }
    }

    private fun getMealSerializableExtra(): MealSerializable {
        val meal: MealSerializable by lazy {
            intent.getSerializableExtra(MEAL_EXTRA) as MealSerializable
        }

        return meal
    }
}