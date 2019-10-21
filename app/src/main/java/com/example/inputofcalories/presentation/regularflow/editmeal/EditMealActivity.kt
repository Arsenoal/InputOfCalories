package com.example.inputofcalories.presentation.regularflow.editmeal

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.inputofcalories.R
import com.example.inputofcalories.presentation.regularflow.model.MealSerializable
import com.example.inputofcalories.presentation.regularflow.viewmeal.MEAL_EXTRA
import kotlinx.android.synthetic.main.activity_edit_meal.*

class EditMealActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_meal)

        updateUi(getMealSerializableExtra())
    }

    private fun getMealSerializableExtra(): MealSerializable {
        val meal: MealSerializable by lazy {
            intent.getSerializableExtra(MEAL_EXTRA) as MealSerializable
        }

        return meal
    }

    private fun updateUi(mealSerializable: MealSerializable) {
        mealTextEditText.setText(mealSerializable.text)
        mealCaloriesEditText.setText(mealSerializable.calories)
        mealWeightEditText.setText(mealSerializable.weight)
    }
}