package com.example.inputofcalories.presentation.regularflow.viewmeal

import android.os.Bundle
import com.example.inputofcalories.R
import com.example.inputofcalories.entity.presentation.regular.BreakfastTime
import com.example.inputofcalories.entity.presentation.regular.DinnerTime
import com.example.inputofcalories.entity.presentation.regular.LunchTime
import com.example.inputofcalories.entity.presentation.regular.SnackTime
import com.example.inputofcalories.presentation.base.BaseActivity
import com.example.inputofcalories.presentation.commonextras.ExtraKeys.MEAL_EXTRA
import com.example.inputofcalories.presentation.navigation.ActivityNavigator
import com.example.inputofcalories.presentation.regularflow.editmeal.EditMealActivity
import com.example.inputofcalories.presentation.regularflow.model.MealSerializable
import kotlinx.android.synthetic.main.activity_view_meal.*
import java.util.*

class ViewMealActivity: BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_meal)

        updateUi(getMealSerializableExtra())

        setupClickListeners()

        setupToolBar()
    }

    private fun getMealSerializableExtra(): MealSerializable {
        val meal: MealSerializable by lazy { intent.getSerializableExtra(MEAL_EXTRA) as MealSerializable }

        return meal
    }

    private fun updateUi(mealSerializable: MealSerializable) = with(mealSerializable) {
        mealTextTextView.text = resources.getString(R.string.name)
        mealTextValueTextView.text = text
        mealCaloriesTextView.text = resources.getString(R.string.calories)
        mealCaloriesValueTextView.text = calories
        mealWeightTextView.text = resources.getString(R.string.weight)
        mealWeightValueTextView.text = weight

        when(timeParam) {
            is BreakfastTime -> { dayTimeTextView.text = getString(R.string.breakfast) }
            is LunchTime -> { dayTimeTextView.text = getString(R.string.lunch) }
            is SnackTime -> { dayTimeTextView.text = getString(R.string.snack) }
            is DinnerTime -> { dayTimeTextView.text = getString(R.string.dinner) }
        }
    }

    private fun setupToolBar() {
        setSupportActionBar(toolbar)

        supportActionBar?.run {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }

        toolbar.title = getMealSerializableExtra().text
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun setupClickListeners() {
        editMealButton.setOnClickListener {
            ActivityNavigator.navigate(this, EditMealActivity::class.java, MEAL_EXTRA, getMealSerializableExtra())
        }
    }
}