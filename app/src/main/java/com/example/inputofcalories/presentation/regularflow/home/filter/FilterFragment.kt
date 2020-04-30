package com.example.inputofcalories.presentation.regularflow.home.filter

import android.view.View
import androidx.lifecycle.lifecycleScope
import com.example.inputofcalories.R
import com.example.inputofcalories.common.extensions.entity.LEFT
import com.example.inputofcalories.common.extensions.entity.RIGHT
import com.example.inputofcalories.common.extensions.makeCornersRounded
import com.example.inputofcalories.entity.presentation.regular.*
import com.example.inputofcalories.presentation.base.BaseFragment
import com.example.inputofcalories.presentation.regularflow.home.RegularUserHomeActivity
import devs.mulham.horizontalcalendar.HorizontalCalendar
import devs.mulham.horizontalcalendar.utils.HorizontalCalendarListener
import kotlinx.android.synthetic.main.fragment_filter.*
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import java.util.*

class FilterFragment: BaseFragment() {

    var mealFilterParams: MutableList<MealFilterParams> = mutableListOf()

    private var dateParams = MealDateParams(
        Calendar.getInstance().get(Calendar.YEAR).toString(),
        Calendar.getInstance().get(Calendar.MONTH).toString(),
        Calendar.getInstance().get(Calendar.DAY_OF_MONTH).toString())

    override fun getLayoutId() = R.layout.fragment_filter

    override fun onLayoutReady(view: View) {
        setupImageCorners()

        setupClickListeners()

        setupCalendar()
    }

    override fun onStop() {
        super.onStop()
        mealFilterParams.clear()
    }

    private fun setupImageCorners() {
        breakfastImageView.makeCornersRounded(LEFT, 22f)
        snackImageView.makeCornersRounded(RIGHT, 22f)
        lunchImageView.makeCornersRounded(LEFT, 22f)
        dinnerImageView.makeCornersRounded(RIGHT, 22f)
    }

    private fun setupCalendar() {
        val startDate = Calendar.getInstance()
        startDate.add(Calendar.MONTH, -2)

        val endDate = Calendar.getInstance()
        endDate.add(Calendar.MONTH, 0)

        val horizontalCalendar = HorizontalCalendar
            .Builder(rootView, calendarView.id)
            .range(startDate, endDate)
            .build()

        horizontalCalendar.calendarListener = object: HorizontalCalendarListener() {
            override fun onDateSelected(date: Calendar?, position: Int) {
                date?.run {
                    dateParams = MealDateParams(
                        year = get(Calendar.YEAR).toString(),
                        month = get(Calendar.MONTH).toString(),
                        dayOfMonth = get(Calendar.DAY_OF_MONTH).toString())
                }

                mealFilterParams.forEach { it.date = dateParams }

                activity?.let { activity ->
                    activity.lifecycleScope.launch {
                        (activity as RegularUserHomeActivity).filterFlow = flow { emit(mealFilterParams) }
                    }
                }
            }
        }
    }

    private fun setupClickListeners() {
        breakfastView.setOnClickListener {
            val breakfastFilter = mealFilterParams.find { it.time == BreakfastTime }

            if(breakfastFilter != null) {
                mealFilterParams.remove(breakfastFilter)
                breakfastView.alpha = 0.5f
            } else {
                mealFilterParams.add(MealFilterParams(dateParams, BreakfastTime))
                breakfastView.alpha = 1f
            }

            activity?.let { activity ->
                activity.lifecycleScope.launch {
                    (activity as RegularUserHomeActivity).filterFlow = flow { emit(mealFilterParams) }
                }
            }
        }
        snackView.setOnClickListener {
            val snackFilter = mealFilterParams.find { it.time == SnackTime }

            if(snackFilter != null) {
                mealFilterParams.remove(snackFilter)
                snackView.alpha = 0.5f
            } else {
                mealFilterParams.add(MealFilterParams(dateParams, SnackTime))
                snackView.alpha = 1f
            }

            activity?.let { activity ->
                activity.lifecycleScope.launch {
                    (activity as RegularUserHomeActivity).filterFlow = flow { emit(mealFilterParams) }
                }
            }
        }
        lunchView.setOnClickListener {
            val lunchFilter = mealFilterParams.find { it.time == LunchTime }

            if(lunchFilter != null) {
                mealFilterParams.remove(lunchFilter)
                lunchView.alpha = 0.5f
            } else {
                mealFilterParams.add(MealFilterParams(dateParams, LunchTime))
                lunchView.alpha = 1f
            }

            activity?.let { activity ->
                activity.lifecycleScope.launch {
                    (activity as RegularUserHomeActivity).filterFlow = flow { emit(mealFilterParams) }
                }
            }
        }
        dinnerView.setOnClickListener {
            val dinnerFilter = mealFilterParams.find { it.time == DinnerTime }

            if(dinnerFilter != null) {
                mealFilterParams.remove(dinnerFilter)
                dinnerView.alpha = 0.5f
            } else {
                mealFilterParams.add(MealFilterParams(dateParams, DinnerTime))
                dinnerView.alpha = 1f
            }

            activity?.let { activity ->
                activity.lifecycleScope.launch {
                    (activity as RegularUserHomeActivity).filterFlow = flow { emit(mealFilterParams) }
                }
            }
        }
    }

    companion object {
        fun newInstance() = FilterFragment()
    }
}