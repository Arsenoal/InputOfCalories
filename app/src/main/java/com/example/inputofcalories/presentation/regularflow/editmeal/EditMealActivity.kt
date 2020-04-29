package com.example.inputofcalories.presentation.regularflow.editmeal

import android.content.Intent
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
import com.example.inputofcalories.presentation.regularflow.addmeal.MealTimeParamHolder
import com.example.inputofcalories.presentation.regularflow.addmeal.MealTimePickerFragment
import com.example.inputofcalories.presentation.regularflow.model.MealSerializable
import com.example.inputofcalories.presentation.regularflow.model.entity.EditMealState
import com.example.inputofcalories.presentation.regularflow.viewmeal.ViewMealActivity
import kotlinx.android.synthetic.main.activity_edit_meal.*
import kotlinx.android.synthetic.main.activity_edit_meal.mealCaloriesEditText
import kotlinx.android.synthetic.main.activity_edit_meal.mealTextEditText
import kotlinx.android.synthetic.main.activity_edit_meal.mealWeightEditText
import kotlinx.android.synthetic.main.activity_edit_meal.pickerFrame
import kotlinx.android.synthetic.main.activity_edit_meal.toolbar
import org.koin.android.viewmodel.ext.android.viewModel

class EditMealActivity: BaseActivity(), MealTimeParamHolder {

    private val editMealViewModel: EditMealViewModel by viewModel()

    private lateinit var mealTime: MealTimeParams

    private val mealTimeLiveData = MutableLiveData<MealTimeParams>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_meal)

        updateUi(getMealSerializableExtra())

        setupClickListeners()
    }

    private fun setupMealTimePickerFragment(currentPosition: Int) {
        FragmentNavigator.openOrReplace(this, MealTimePickerFragment.newInstance(currentPosition), pickerFrame.id)

        getMealTimeLiveData().observe(this, Observer { mealTime = it })
    }

    private fun updateUi(mealSerializable: MealSerializable) = with(mealSerializable) {
        mealTextEditText.setText(text)
        mealCaloriesEditText.setText(calories)
        mealWeightEditText.setText(weight)

        mealTime = timeParam

        setupToolbar()

        val currentPosition = when(timeParam) {
            is BreakfastTime -> 0
            is LunchTime -> 1
            is SnackTime -> 2
            is DinnerTime -> 3
            else -> 0
        }

        setupMealTimePickerFragment(currentPosition)
    }

    private fun setupToolbar() {
        setSupportActionBar(toolbar)

        supportActionBar?.run {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }
    }

    private fun setupClickListeners() {
        saveButton.setOnClickListener {
            getMealSerializableExtra().let { mealSerializable ->

                val meal = with(mealSerializable) {
                    Meal(
                        id = id,
                        params = MealParams(text = mealTextEditText.text.toString(), calories = mealCaloriesEditText.text.toString(), weight = mealWeightEditText.text.toString()),
                        filterParams = MealFilterParams(date = MealDateParams(
                            year = year, month = month, dayOfMonth = dayOfMonth),
                            time = mealTime)
                    )
                }

                this@EditMealActivity.run {
                    editMealViewModel.editClicked(meal).observe(this, Observer { state ->
                        when(state) {
                            EditMealState.EditMealSucceed -> {
                                ToastManager.showToastShort(this, resources.getString(R.string.meal_edit_succeed))
                                ActivityNavigator.navigateAndFinishCurrent(this, ViewMealActivity::class.java,
                                    Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP,
                                    MEAL_EXTRA, meal.toMealSerializable())
                            }
                            EditMealState.EditMealFailed -> { ToastManager.showToastShort(this, "failed to edit meal") }
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

    //TODO don't forget to encapsulate alonge with toolbar
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun getMealTimeLiveData() = mealTimeLiveData
}