package com.example.inputofcalories.presentation.regularflow.editmeal

import android.os.Bundle
import androidx.lifecycle.Observer
import com.example.inputofcalories.R
import com.example.inputofcalories.entity.presentation.regular.*
import com.example.inputofcalories.presentation.base.BaseActivity
import com.example.inputofcalories.presentation.common.ToastManager
import com.example.inputofcalories.presentation.commonextras.ExtraKeys.MEAL_EXTRA
import com.example.inputofcalories.presentation.navigation.ActivityNavigator
import com.example.inputofcalories.presentation.regularflow.model.MealSerializable
import com.example.inputofcalories.presentation.regularflow.model.entity.EditMealState
import kotlinx.android.synthetic.main.activity_edit_meal.*
import org.koin.android.viewmodel.ext.android.viewModel

class EditMealActivity: BaseActivity() {

    private val editMealViewModel: EditMealViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_meal)

        updateUi(getMealSerializableExtra())

        setupClickListeners()

    }

    private fun updateUi(mealSerializable: MealSerializable) {
        mealTextEditText.setText(mealSerializable.text)
        mealCaloriesEditText.setText(mealSerializable.calories)
        mealWeightEditText.setText(mealSerializable.weight)
    }

    private fun setupClickListeners() {
        editMealButton.setOnClickListener {
            getMealSerializableExtra().run {

                val meal = Meal(
                    id = id,
                    params = MealParams(
                        text = mealTextEditText.text.toString(),
                        calories = mealCaloriesEditText.text.toString(),
                        weight = mealWeightEditText.text.toString()),
                    filterParams = MealFilterParams(
                        date = MealDateParams(year = year, month = month, dayOfMonth = dayOfMonth),
                        time = timeParam
                    )
                )

                this@EditMealActivity.run {
                    editMealViewModel.editClicked(meal).observe(this, Observer { state ->
                        when(state) {
                            EditMealState.EditMealSucceed -> {
                                ToastManager.showToastShort(this, resources.getString(R.string.meal_edit_succeed))
                                ActivityNavigator.finish(this)
                            }
                            EditMealState.EditMealFailed -> {
                                ToastManager.showToastShort(this, "failed to edit meal")
                            }
                        }
                    })
                }
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