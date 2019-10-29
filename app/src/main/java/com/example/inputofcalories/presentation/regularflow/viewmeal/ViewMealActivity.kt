package com.example.inputofcalories.presentation.regularflow.viewmeal

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.example.inputofcalories.R
import com.example.inputofcalories.presentation.commonextras.ExtraKeys.MEAL_EXTRA
import com.example.inputofcalories.presentation.navigation.ActivityNavigator
import com.example.inputofcalories.presentation.regularflow.addmeal.AddMealActivity
import com.example.inputofcalories.presentation.regularflow.editmeal.EditMealActivity
import com.example.inputofcalories.presentation.regularflow.model.MealSerializable
import kotlinx.android.synthetic.main.activity_view_meal.*
import java.util.*

class ViewMealActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_meal)

        updateUi(getMealSerializableExtra())

        setupToolBar()

        setupClickListeners()
    }

    private fun getMealSerializableExtra(): MealSerializable {
        val meal: MealSerializable by lazy {
            intent.getSerializableExtra(MEAL_EXTRA) as MealSerializable
        }

        return meal
    }

    private fun updateUi(mealSerializable: MealSerializable) {
        mealTextTextView.text = String.format(Locale.ENGLISH, "%s: %s", resources.getString(R.string.text), mealSerializable.text)
        mealCaloriesTextView.text = String.format(Locale.ENGLISH, "%s: %s", resources.getString(R.string.calories), mealSerializable.calories)
        mealWeightTextView.text = String.format(Locale.ENGLISH, "%s: %s", resources.getString(R.string.weight), mealSerializable.weight)
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