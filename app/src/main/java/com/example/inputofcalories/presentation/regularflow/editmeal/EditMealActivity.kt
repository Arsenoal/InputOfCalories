package com.example.inputofcalories.presentation.regularflow.editmeal

import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.example.inputofcalories.R
import com.example.inputofcalories.entity.presentation.regular.*
import com.example.inputofcalories.presentation.base.BaseActivity
import com.example.inputofcalories.presentation.common.ToastManager
import com.example.inputofcalories.presentation.commonextras.ExtraKeys.MEAL_EXTRA
import com.example.inputofcalories.presentation.navigation.ActivityNavigator
import com.example.inputofcalories.presentation.navigation.FragmentNavigator
import com.example.inputofcalories.presentation.regularflow.addmeal.DaytimePickerFragment
import com.example.inputofcalories.presentation.regularflow.addmeal.MealTimeParamHolder
import com.example.inputofcalories.presentation.regularflow.model.MealSerializable
import com.example.inputofcalories.presentation.regularflow.model.entity.EditMealState
import kotlinx.android.synthetic.main.activity_edit_meal.*
import kotlinx.android.synthetic.main.activity_edit_meal.dateTimeView
import kotlinx.android.synthetic.main.activity_edit_meal.mealCaloriesEditText
import kotlinx.android.synthetic.main.activity_edit_meal.mealTextEditText
import kotlinx.android.synthetic.main.activity_edit_meal.mealWeightEditText
import org.koin.android.viewmodel.ext.android.viewModel

class EditMealActivity: BaseActivity(), MealTimeParamHolder {

    private val editMealViewModel: EditMealViewModel by viewModel()

    private val dateTimePickerFragment = DaytimePickerFragment.newInstance()

    private lateinit var mealTime: MealTimeParams

    private val mealTimeLiveData = MutableLiveData<MealTimeParams>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_meal)

        updateUi(getMealSerializableExtra())

        setupClickListeners()

        setupDaytimeFragment()
    }

    private fun updateUi(mealSerializable: MealSerializable) = with(mealSerializable) {
        mealTextEditText.setText(text)
        mealCaloriesEditText.setText(calories)
        mealWeightEditText.setText(weight)

        mealTime = timeParam

        setupDaytimeTextWithTimeParams(timeParam)
    }

    private fun setupClickListeners() {
        saveButton.setOnClickListener {
            getMealSerializableExtra().run {

                val meal = Meal(
                    id = id,
                    params = MealParams(text = mealTextEditText.text.toString(), calories = mealCaloriesEditText.text.toString(), weight = mealWeightEditText.text.toString()),
                    filterParams = MealFilterParams(date = MealDateParams(year = year, month = month, dayOfMonth = dayOfMonth), time = mealTime)
                )

                this@EditMealActivity.run {
                    editMealViewModel.editClicked(meal).observe(this, Observer { state ->
                        when(state) {
                            EditMealState.EditMealSucceed -> {
                                ToastManager.showToastShort(this, resources.getString(R.string.meal_edit_succeed))
                                ActivityNavigator.finish(this)
                            }
                            EditMealState.EditMealFailed -> { ToastManager.showToastShort(this, "failed to edit meal") }
                        }
                    })
                }
            }
        }

        dateTimeView.setOnClickListener {
            FragmentNavigator.openOrReplace(this, dateTimePickerFragment, timePickerFrame.id, DaytimePickerFragment::class.java.name)
        }
    }

    private fun setupDaytimeFragment() {
        getMealTimeLiveData().observe(this, Observer { timeParams ->
            mealTime = timeParams

            setupDaytimeTextWithTimeParams(timeParams)

            FragmentNavigator.remove(this, dateTimePickerFragment)
        })
    }

    private fun getMealSerializableExtra(): MealSerializable {
        val meal: MealSerializable by lazy {
            intent.getSerializableExtra(MEAL_EXTRA) as MealSerializable
        }

        return meal
    }

    private fun setupDaytimeTextWithTimeParams(timeParams: MealTimeParams) {
        when(timeParams) {
            is BreakfastTime -> { daytimeTextView.text = getString(R.string.breakfast) }
            is LunchTime -> { daytimeTextView.text = getString(R.string.lunch) }
            is DinnerTime -> { daytimeTextView.text = getString(R.string.dinner) }
            is SnackTime -> { daytimeTextView.text = getString(R.string.snack) }
        }
    }

    override fun getMealTimeLiveData() = mealTimeLiveData
}