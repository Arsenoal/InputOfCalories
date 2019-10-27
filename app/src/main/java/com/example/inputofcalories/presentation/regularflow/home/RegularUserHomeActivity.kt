package com.example.inputofcalories.presentation.regularflow.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.View.VISIBLE
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.inputofcalories.R
import com.example.inputofcalories.presentation.ProgressView
import com.example.inputofcalories.presentation.ToastManager
import com.example.inputofcalories.presentation.commonextras.ExtraKeys.MEAL_EXTRA
import com.example.inputofcalories.presentation.commonextras.ExtraKeys.USER_ID_KEY
import com.example.inputofcalories.presentation.navigation.ActivityNavigator
import com.example.inputofcalories.presentation.regularflow.addmeal.AddMealActivity
import com.example.inputofcalories.presentation.regularflow.model.MealSerializable
import com.example.inputofcalories.presentation.regularflow.viewmeal.ViewMealActivity
import kotlinx.android.synthetic.main.activity_regular_user_home.*
import kotlinx.android.synthetic.main.activity_regular_user_home.addMealButton
import kotlinx.android.synthetic.main.activity_regular_user_home.toolbar
import kotlinx.android.synthetic.main.progress_layout.*
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class RegularUserHomeActivity : AppCompatActivity(), ProgressView {

    private lateinit var mealsProviderViewModel: MealsProviderViewModel

    private lateinit var deleteMealViewModel: DeleteMealViewModel

    private lateinit var updateDailyCaloriesViewModel: UpdateDailyCaloriesViewModel

    private lateinit var checkDailyLimitViewModel: CheckDailyLimitViewModel

    private val mealsAdapter = MealsRecyclerAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_regular_user_home)

        setupDeleteMealViewModel()

        setupUpdateDailyCaloriesViewModel()

        setupDailyLimitCheckerViewModel()

        setupMealsRecyclerView()

        setupToolBar()

        setupClickListeners()

        checkDailyCaloriesLimit()
    }

    override fun onResume() {
        super.onResume()

        setupViewModel()
    }

    private fun setupViewModel() {
        val mealsProviderViewModel: MealsProviderViewModel by viewModel{ parametersOf(getUserIdExtra()) }
        this.mealsProviderViewModel = mealsProviderViewModel

        this.mealsProviderViewModel.let {
            it.mealsLoadFailLiveData.observe(this, Observer {
                hideProgress()
                ToastManager.showToastShort(this, resources.getString(R.string.failed_to_load_meals))
            })

            it.mealsLoadSuccessLiveData.observe(this, Observer { list ->
                hideProgress()
                mealsAdapter.setItems(list)
            })

            it.noMealsFoundLiveData.observe(this, Observer {
                hideProgress()
                setupEmptyMealsUi()
            })

            showProgress()
            it.getMeals()
        }
    }

    private fun setupDeleteMealViewModel() {
        val deleteMealViewModel: DeleteMealViewModel by viewModel{ parametersOf(getUserIdExtra()) }
        this.deleteMealViewModel = deleteMealViewModel

        this.deleteMealViewModel.let {
            it.deleteMealFailLiveData.observe(this, Observer {
                hideProgress()

                ToastManager.showToastShort(this, resources.getString(R.string.delete_meal_failed))
            })

            it.deleteMealSuccessLiveData.observe(this, Observer {
                ToastManager.showToastShort(this, resources.getString(R.string.delete_meal_succeed))

                showProgress()
                mealsProviderViewModel.getMeals()
            })
        }
    }

    private fun setupUpdateDailyCaloriesViewModel() {
        val updateDailyCaloriesViewModel: UpdateDailyCaloriesViewModel by viewModel{ parametersOf(getUserIdExtra()) }
        this.updateDailyCaloriesViewModel = updateDailyCaloriesViewModel

        this.updateDailyCaloriesViewModel.let {
            it.updateCaloriesSucceedLiveData.observe(this, Observer {
                hideProgress()
                ToastManager.showToastShort(this, resources.getString(R.string.update_succeed))
            })

            it.updateCaloriesFailedLiveData.observe(this, Observer {
                hideProgress()
                ToastManager.showToastShort(this, resources.getString(R.string.update_failed))
            })
        }
    }

    private fun setupDailyLimitCheckerViewModel() {
        val checkDailyLimitViewModel: CheckDailyLimitViewModel by viewModel{ parametersOf(getUserIdExtra()) }
        this.checkDailyLimitViewModel = checkDailyLimitViewModel

        this.checkDailyLimitViewModel.let {
            it.dailyLimitExceededLiveData.observe(this, Observer {
                //TODO mark list with red color
                hideProgress()
                ToastManager.showToastShort(this, resources.getString(R.string.daily_calories_limit_exceeded))
            })

            it.dailyLimitNotExceededLiveData.observe(this, Observer {
                //TODO mark list with green color
                hideProgress()
                ToastManager.showToastShort(this, resources.getString(R.string.daily_calories_limit_not_exceeded))
            })

            it.failedToCheckDailyLimitLiveData.observe(this, Observer {
                hideProgress()
                ToastManager.showToastShort(this, resources.getString(R.string.failed_to_check_daily_calories_limit))
            })
        }
    }

    private fun checkDailyCaloriesLimit() {
        checkDailyLimitViewModel.checkDailyLimit()
    }

    private fun setupEmptyMealsUi() {
        noMealsToShowTextView.visibility = VISIBLE
    }

    private fun setupMealsRecyclerView() {
        mealsRecyclerView.layoutManager = LinearLayoutManager(this)
        mealsRecyclerView.adapter = mealsAdapter

        mealsAdapter.mealSelectedLiveData.observe(this, Observer {
            val mealSerializable = MealSerializable(
                id = it.id,
                text = it.params.text,
                calories = it.params.calories,
                weight = it.params.weight,
                year = it.filterParams.date.year,
                month = it.filterParams.date.month,
                dayOfMonth = it.filterParams.date.dayOfMonth,
                from = it.filterParams.time.from,
                to = it.filterParams.time.to)

            ActivityNavigator.navigate(this, ViewMealActivity::class.java, MEAL_EXTRA, mealSerializable)
        })

        mealsAdapter.mealDeleteClickedLiveData.observe(this, Observer { mealId ->
            showProgress()
            deleteMealViewModel.deleteMealClicked(mealId)
        })
    }

    private fun setupToolBar() {
        setSupportActionBar(toolbar)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_regular_user_home, menu)
        return true
    }

    private fun setupClickListeners() {
        addMealButton.setOnClickListener {
            ActivityNavigator.navigate(this, AddMealActivity::class.java)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.action_filter -> {
                val dialog = FilterDialog(this)
                dialog.applyFilterLiveData.observe(this, Observer { mealFilterParams ->
                    showProgress()
                    mealsProviderViewModel.getMealsFiltered(mealFilterParams)
                })
                dialog.show()
            }
            R.id.daily_calories -> {
                val dialog = SetDailyCaloriesDialog(this)
                dialog.dailyCaloriesLiveData.observe(this, Observer { dailyCalories ->
                    showProgress()
                    updateDailyCaloriesViewModel.applyClicked(dailyCalories)
                })
                dialog.show()
            }
        }

        return true
    }

    private fun getUserIdExtra() = intent.getStringExtra(USER_ID_KEY)

    override fun showProgress() {
        progressContainer.visibility = VISIBLE
    }

    override fun hideProgress() {
        progressContainer.visibility = View.GONE
    }
}
