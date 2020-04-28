package com.example.inputofcalories.presentation.regularflow.viewmeal

import android.content.Intent
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
import com.example.inputofcalories.presentation.regularflow.home.RegularUserHomeActivity
import com.example.inputofcalories.presentation.regularflow.model.MealSerializable
import kotlinx.android.synthetic.main.activity_view_meal.*

class ViewMealActivity: BaseActivity() {

    private var mealSerializable: MealSerializable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_meal)

        initMealSerializable(intent)

        updateUi(mealSerializable)

        setupClickListeners()

        setupToolBar()
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)

        initMealSerializable(intent)

        updateUi(mealSerializable)
    }

    private fun initMealSerializable(intent: Intent?) {
        mealSerializable = intent?.run { getSerializableExtra(MEAL_EXTRA) as MealSerializable }
    }

    private fun updateUi(mealSerializable: MealSerializable?) = mealSerializable?.run {
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

        toolbar.title = mealSerializable?.text
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun setupClickListeners() {
        editMealButton.setOnClickListener {
            mealSerializable?.let {
                ActivityNavigator.navigate(this, EditMealActivity::class.java, MEAL_EXTRA, it)
            }
        }
    }
}