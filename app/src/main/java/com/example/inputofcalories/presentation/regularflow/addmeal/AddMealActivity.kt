package com.example.inputofcalories.presentation.regularflow.addmeal

import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.example.inputofcalories.R
import com.example.inputofcalories.common.extensions.empty
import com.example.inputofcalories.entity.presentation.regular.*
import com.example.inputofcalories.presentation.base.BaseActivity
import com.example.inputofcalories.presentation.common.ToastManager
import com.example.inputofcalories.presentation.navigation.ActivityNavigator
import com.example.inputofcalories.presentation.navigation.FragmentNavigator
import com.example.inputofcalories.presentation.regularflow.model.entity.AddMealState
import devs.mulham.horizontalcalendar.HorizontalCalendar
import devs.mulham.horizontalcalendar.utils.HorizontalCalendarListener
import kotlinx.android.synthetic.main.add_meal_activity.*
import kotlinx.android.synthetic.main.add_meal_activity.calendarView
import org.koin.android.viewmodel.ext.android.viewModel
import java.util.*

class AddMealActivity: BaseActivity() {

    private val addMealViewModel: AddMealViewModel by viewModel()

    private var mealTime: MealTimeParams = LunchTime

    private var mealDateParams = with(Calendar.getInstance()) {
        MealDateParams(get(Calendar.YEAR).toString(), get(Calendar.MONTH).toString(), get(Calendar.DAY_OF_MONTH).toString())
    }

    private val addMealFragment = DaytimePickerFragment.newInstance()

    val mealTimeLiveData = MutableLiveData<MealTimeParams>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_meal_activity)

        setupCalendar()

        setupDaytimeFragment()

        setupClickListeners()
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
                    val year = get(Calendar.YEAR).toString()
                    val month = get(Calendar.MONTH).toString()
                    val dayOfMonth = get(Calendar.DAY_OF_MONTH).toString()

                    mealDateParams = MealDateParams(year, month, dayOfMonth)
                }
            }
        }
    }

    private fun setupClickListeners() {
        addMealButton.setOnClickListener {
            val params = MealParams(
                text = mealTextEditText.text.toString(),
                calories = mealCaloriesEditText.text.toString(),
                weight = mealWeightEditText.text.toString())

            val filterParams = MealFilterParams(mealDateParams, mealTime)

            addMealViewModel.addMeal(params, filterParams).observe(this, Observer { state ->
                when(state) {
                    AddMealState.AddMealSucceed -> {
                        ToastManager.showToastShort(this, resources.getString(R.string.meal_successfully_added))
                        ActivityNavigator.finish(this)
                    }
                    AddMealState.AddMealFailed -> {
                        ToastManager.showToastShort(this, resources.getString(R.string.meal_add_failed))
                    }
                }
            })
        }
    }

    private fun setupDaytimeFragment() {
        FragmentNavigator.openOrReplace(this, addMealFragment, timePickerFrame.id, DaytimePickerFragment::class.java.name)

        mealTimeLiveData.observe(this, Observer {
            mealTime = it
            FragmentNavigator.remove(this, addMealFragment)
        })
    }

    override fun onBackPressed() {
        ActivityNavigator.finish(this)
    }
}