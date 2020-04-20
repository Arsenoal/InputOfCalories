package com.example.inputofcalories.presentation.regularflow.viewmeal

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.example.inputofcalories.R
import com.example.inputofcalories.entity.presentation.regular.BreakfastTime
import com.example.inputofcalories.entity.presentation.regular.DinnerTime
import com.example.inputofcalories.entity.presentation.regular.LunchTime
import com.example.inputofcalories.entity.presentation.regular.SnackTime
import com.example.inputofcalories.presentation.base.BaseActivity
import com.example.inputofcalories.presentation.commonextras.ExtraKeys.MEAL_EXTRA
import com.example.inputofcalories.presentation.navigation.ActivityNavigator
import com.example.inputofcalories.presentation.regularflow.addmeal.AddMealActivity
import com.example.inputofcalories.presentation.regularflow.editmeal.EditMealActivity
import com.example.inputofcalories.presentation.regularflow.model.MealSerializable
import kotlinx.android.synthetic.main.activity_view_meal.*
import java.util.*

class ViewMealActivity: BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_meal)

        updateUi(getMealSerializableExtra())

        setupToolBar()

        setupClickListeners()
    }

    private fun getMealSerializableExtra(): MealSerializable {
        val meal: MealSerializable by lazy { intent.getSerializableExtra(MEAL_EXTRA) as MealSerializable }

        return meal
    }

    private fun updateUi(mealSerializable: MealSerializable) = with(mealSerializable) {
        mealTextTextView.text = String.format(Locale.ENGLISH, "%s: %s", resources.getString(R.string.text), text)
        mealCaloriesTextView.text = String.format(Locale.ENGLISH, "%s: %s", resources.getString(R.string.calories), calories)
        mealWeightTextView.text = String.format(Locale.ENGLISH, "%s: %sg", resources.getString(R.string.weight), weight)

        when(timeParam) {
            is BreakfastTime -> { dayTimeTextView.text = getString(R.string.breakfast) }
            is LunchTime -> { dayTimeTextView.text = getString(R.string.lunch) }
            is SnackTime -> { dayTimeTextView.text = getString(R.string.snack) }
            is DinnerTime -> { dayTimeTextView.text = getString(R.string.dinner) }
        }
    }

    private fun setupToolBar() {
        setSupportActionBar(toolbar)
    }

    private fun setupClickListeners() {
        addMealButton.setOnClickListener {
            ActivityNavigator.navigate(this, AddMealActivity::class.java)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_view_meal, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.action_edit -> {
                ActivityNavigator.navigate(this, EditMealActivity::class.java, MEAL_EXTRA, getMealSerializableExtra())
            }
        }

        return true
    }
}