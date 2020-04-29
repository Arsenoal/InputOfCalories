package com.example.inputofcalories.presentation.regularflow.addmeal

import android.os.Bundle
import android.view.View
import androidx.lifecycle.lifecycleScope
import com.example.inputofcalories.R
import com.example.inputofcalories.common.extensions.onScrolled
import com.example.inputofcalories.entity.presentation.regular.BreakfastTime
import com.example.inputofcalories.entity.presentation.regular.DinnerTime
import com.example.inputofcalories.entity.presentation.regular.LunchTime
import com.example.inputofcalories.entity.presentation.regular.SnackTime
import com.example.inputofcalories.presentation.base.BaseFragment
import com.example.inputofcalories.presentation.regularflow.addmeal.adapter.MealTimePickerAdapter
import com.example.inputofcalories.presentation.regularflow.addmeal.adapter.model.MealTimePickerParams
import com.yarolegovich.discretescrollview.DiscreteScrollView
import com.yarolegovich.discretescrollview.transform.Pivot
import com.yarolegovich.discretescrollview.transform.ScaleTransformer
import kotlinx.android.synthetic.main.fragment_meal_time_picker.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

const val CURRENT_TIME = "current_time_extra"

class MealTimePickerFragment: BaseFragment() {

    private val params = listOf(
        MealTimePickerParams(BreakfastTime),
        MealTimePickerParams(LunchTime),
        MealTimePickerParams(SnackTime),
        MealTimePickerParams(DinnerTime))

    override fun getLayoutId() = R.layout.fragment_meal_time_picker

    override fun onLayoutReady(view: View) {
        mealTimePicker.run {
            adapter = MealTimePickerAdapter(params)
            setOffscreenItems(4)
            setOverScrollEnabled(true)
            setSlideOnFling(true)

            setItemTransformer(
                ScaleTransformer.Builder()
                    .setMaxScale(1.05f)
                    .setMinScale(0.8f)
                    .setPivotX(Pivot.X.CENTER)
                    .setPivotY(Pivot.Y.CENTER)
                    .build())
        }

        viewLifecycleOwner.lifecycleScope.launch {
            delay(500)
            withContext(Dispatchers.Main) {
                val args = arguments

                if (args != null) {
                    val position = args.getInt(CURRENT_TIME) - 1

                    mealTimePicker.smoothScrollToPosition(position)

                    setupOnPickerScrolled()
                } else { setupOnPickerScrolled() }
            }
        }
    }

    private fun setupOnPickerScrolled() {
        mealTimePicker.onScrolled {
            val mHolder = (activity as MealTimeParamHolder)
            mHolder.getMealTimeLiveData().value = params[it].mealTime
        }
    }

    companion object {
        fun newInstance(currentPos: Int? = null): MealTimePickerFragment {
            val fragment = MealTimePickerFragment()

            currentPos?.let { position ->
                val arguments = Bundle()
                //as def value in null case is 0 we have to increment position here and decrement on get, ty for attention
                val incrementedPos = position + 1
                arguments.putInt(CURRENT_TIME, incrementedPos)
                fragment.arguments = arguments
            }

            return fragment
        }
    }
}