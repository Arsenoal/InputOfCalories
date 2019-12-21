package com.example.inputofcalories.presentation.regularflow.home

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import com.example.inputofcalories.R
import com.example.inputofcalories.entity.presentation.regular.*
import kotlinx.android.synthetic.main.dialog_filter.*
import java.util.*

class FilterDialog(context: Context): Dialog(context) {

    val applyFilterLiveData = MutableLiveData<MealFilterParams>()

    private var mealFilterParams: MealFilterParams = MealFilterParams(
        MealDateParams(
            Calendar.getInstance().get(Calendar.YEAR).toString(),
            Calendar.getInstance().get(Calendar.MONTH).toString(),
            Calendar.getInstance().get(Calendar.DAY_OF_MONTH).toString()),
        LunchTime
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_filter)

        setupClickListeners()

        setupMealTimePickerListener()

        setupMealDatePickerListener()
    }

    private fun setupClickListeners() {
        applyButton.setOnClickListener {
            applyFilterLiveData.value = mealFilterParams
            dismiss()
        }
    }

    private fun setupMealTimePickerListener() {
        mealDateRadioGroup.setOnCheckedChangeListener { _, id ->
            mealFilterParams.run {
                when(id) {
                    R.id.breakfastButton -> { time = BreakfastTime }
                    R.id.lunchButton -> { time = LunchTime }
                    R.id.dinnerButton -> { time = DinnerTime }
                    R.id.snackButton -> { time = SnackTime }
                }
            }
        }
    }

    private fun setupMealDatePickerListener() {
        calendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->
            val dateParams = MealDateParams(year.toString(), month.toString(), dayOfMonth.toString())

            mealFilterParams.date = dateParams
        }
    }
}