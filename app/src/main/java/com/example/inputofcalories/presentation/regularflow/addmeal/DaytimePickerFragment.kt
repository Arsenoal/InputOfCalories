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
            viewLifecycleOwner.lifecycleScope.launch {
                delay(150)
                withContext(Dispatchers.Main) {
                    (activity as AddMealActivity).mealTimeLiveData.value = BreakfastTime
                }
            }
        }
        snackView.setOnClickListener {
            viewLifecycleOwner.lifecycleScope.launch {
                delay(150)
                withContext(Dispatchers.Main) {
                    (activity as AddMealActivity).mealTimeLiveData.value = SnackTime
                }
            }
        }
        lunchView.setOnClickListener {
            viewLifecycleOwner.lifecycleScope.launch {
                delay(150)
                withContext(Dispatchers.Main) {
                    (activity as AddMealActivity).mealTimeLiveData.value = LunchTime
                }
            }
        }
        dinnerView.setOnClickListener {
            viewLifecycleOwner.lifecycleScope.launch {
                delay(150)
                withContext(Dispatchers.Main) {
                    (activity as AddMealActivity).mealTimeLiveData.value = DinnerTime
                }
            }
        }
    }

    companion object {
        fun newInstance() = DaytimePickerFragment()
    }
}