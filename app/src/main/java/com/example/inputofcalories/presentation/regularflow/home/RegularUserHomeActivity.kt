package com.example.inputofcalories.presentation.regularflow.home

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.View.VISIBLE
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.inputofcalories.R
import com.example.inputofcalories.presentation.regularflow.home.RegularFlowObserversFactory.ObservableKey
import com.example.inputofcalories.presentation.base.BaseActivity
import com.example.inputofcalories.presentation.common.ProgressView
import com.example.inputofcalories.presentation.commonextras.ExtraKeys.MEAL_EXTRA
import com.example.inputofcalories.presentation.navigation.ActivityNavigator
import com.example.inputofcalories.presentation.navigation.FragmentNavigator
import com.example.inputofcalories.presentation.regularflow.addmeal.AddMealActivity
import com.example.inputofcalories.presentation.regularflow.home.filter.FilterFragment
import com.example.inputofcalories.presentation.regularflow.home.viewmodel.*
import com.example.inputofcalories.presentation.regularflow.model.MealSerializable
import com.example.inputofcalories.presentation.regularflow.viewmeal.ViewMealActivity
import kotlinx.android.synthetic.main.activity_regular_user_home.*
import kotlinx.android.synthetic.main.activity_regular_user_home.addMealButton
import kotlinx.android.synthetic.main.progress_layout.*
import org.koin.android.viewmodel.ext.android.viewModel

class RegularUserHomeActivity : BaseActivity(), ProgressView {

    private val mealsViewModel: MealsViewModel by viewModel()

    private val dailyCaloriesViewModel: DailyCaloriesViewModel by viewModel()

    private val filterFragment = FilterFragment.newInstance()

    private lateinit var observerFactory: RegularFlowObserversFactory

    private val mealsAdapter = MealsRecyclerAdapter()

    private var isFilterOpened = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_regular_user_home)

        setSupportActionBar(toolbar)

        initObserverFactory()

        setupMealsRecyclerView()

        setupClickListeners()

        checkDailyCaloriesLimit()
    }

    private fun initObserverFactory() {
        observerFactory = RegularFlowObserversFactory(this, mealsAdapter)
    }

    fun loadMeals() {
        mealsViewModel.getMeals().observe(this, observerFactory.get(ObservableKey.GetMealsObserver))
    }

    private fun checkDailyCaloriesLimit() {
        dailyCaloriesViewModel
            .checkDailyLimit()
            .observe(this, observerFactory.get(ObservableKey.CheckDailyLimitObserver))
    }

    fun showEmptyMealsUi() {
        noMealsToShowTextView.visibility = VISIBLE
    }

    fun showReloadMealsOption() {

    }

    private fun setupMealsRecyclerView() {
        mealsRecyclerView.layoutManager = LinearLayoutManager(this)
        mealsRecyclerView.adapter = mealsAdapter

        mealsAdapter.mealSelectedLiveData.observe(this, Observer {
            val mealSerializable = with(it) {
                MealSerializable(id, text, calories, weight, year, month, dayOfMonth, timeParams.from, timeParams.to)
            }

            ActivityNavigator.navigate(this, ViewMealActivity::class.java, MEAL_EXTRA, mealSerializable)
        })

        mealsAdapter.mealDeleteClickedLiveData.observe(this, Observer { mealId ->
            showProgress()
            mealsViewModel.deleteMeal(mealId).observe(this, observerFactory.get(ObservableKey.DeleteMealObserver))
        })
    }

    private fun setupClickListeners() {
        addMealButton.setOnClickListener {
            ActivityNavigator.navigate(this, AddMealActivity::class.java)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_regular_user_home, menu);
        return true;
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.actionFilter -> {
                isFilterOpened = if(!isFilterOpened) {
                    FragmentNavigator.openOrReplace(this, filterFragment, filterFrame.id, null)
                    true
                } else {
                    FragmentNavigator.remove(this, filterFragment)
                    false
                }
            }
            R.id.dailyCalories -> {
                val dialog = SetDailyCaloriesDialog(this)

                dialog.dailyCaloriesLiveData.observe(this, Observer { dailyCalories ->
                    showProgress()

                    dailyCaloriesViewModel.saveDailyCaloriesLimit(dailyCalories).observe(this, observerFactory.get(ObservableKey.UpdateDailyCaloriesObserver))
                })
                dialog.show()
            }
        }

        return true
    }

    override fun showProgress() { progressContainer.visibility = VISIBLE }

    override fun hideProgress() { progressContainer.visibility = View.GONE }
}
