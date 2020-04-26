package com.example.inputofcalories.presentation.regularflow.addmeal

import android.view.View
import androidx.lifecycle.lifecycleScope
import com.example.inputofcalories.R
import com.example.inputofcalories.entity.presentation.regular.BreakfastTime
import com.example.inputofcalories.entity.presentation.regular.DinnerTime
import com.example.inputofcalories.entity.presentation.regular.LunchTime
import com.example.inputofcalories.entity.presentation.regular.SnackTime
import com.example.inputofcalories.presentation.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_daytime_picker.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DaytimePickerFragment: BaseFragment() {

    override fun getLayoutId() = R.layout.fragment_daytime_picker

    override fun onLayoutReady(view: View) {
        setupClickListeners()
    }

    private fun setupClickListeners() {
        breakfastView.setOnClickListener {
            val mHolder = (activity as MealTimeParamHolder)

            viewLifecycleOwner.lifecycleScope.launch {
                delay(150)
                withContext(Dispatchers.Main) {
                    mHolder.getMealTimeLiveData().value = BreakfastTime
                }
            }
        }
        snackView.setOnClickListener {
            val mHolder = (activity as MealTimeParamHolder)

            viewLifecycleOwner.lifecycleScope.launch {
                delay(150)
                withContext(Dispatchers.Main) {
                    mHolder.getMealTimeLiveData().value = SnackTime
                }
            }
        }
        lunchView.setOnClickListener {
            val mHolder = (activity as MealTimeParamHolder)

            viewLifecycleOwner.lifecycleScope.launch {
                delay(150)
                withContext(Dispatchers.Main) {
                    mHolder.getMealTimeLiveData().value = LunchTime
                }
            }
        }
        dinnerView.setOnClickListener {
            viewLifecycleOwner.lifecycleScope.launch {
                val mHolder = (activity as MealTimeParamHolder)

                delay(150)
                withContext(Dispatchers.Main) {
                    mHolder.getMealTimeLiveData().value = DinnerTime
                }
            }
        }
    }

    companion object {
        fun newInstance() = DaytimePickerFragment()
    }
}