package com.example.inputofcalories.presentation.adminflow.usermeals

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.inputofcalories.R
import com.example.inputofcalories.presentation.ToastManager
import com.example.inputofcalories.presentation.commonextras.ExtraKeys.MEAL_EXTRA
import com.example.inputofcalories.presentation.commonextras.ExtraKeys.USER_ID_KEY
import com.example.inputofcalories.presentation.navigation.ActivityNavigator
import com.example.inputofcalories.presentation.regularflow.addmeal.AddMealActivity
import com.example.inputofcalories.presentation.regularflow.home.DeleteMealViewModel
import com.example.inputofcalories.presentation.regularflow.home.MealsProviderViewModel
import com.example.inputofcalories.presentation.regularflow.home.MealsRecyclerAdapter
import com.example.inputofcalories.presentation.regularflow.home.UpdateDailyCaloriesViewModel
import com.example.inputofcalories.presentation.regularflow.home.model.MealAdapterModel
import com.example.inputofcalories.presentation.regularflow.model.MealSerializable
import com.example.inputofcalories.presentation.regularflow.viewmeal.ViewMealActivity
import kotlinx.android.synthetic.main.activity_regular_user_home.*
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class UserMealsActivity: AppCompatActivity() {

    private lateinit var deleteMealViewModel: DeleteMealViewModel

    private lateinit var updateDailyCaloriesViewModel: UpdateDailyCaloriesViewModel

    private lateinit var mealsProviderViewModel: MealsProviderViewModel

    private val mealsAdapter = MealsRecyclerAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_regular_user_home)

        setupMealsProviderViewModel()

        setupDeleteMealViewModel()

        setupUpdateDailyCaloriesViewModel()

        setupMealsRecyclerView()

        setupClickListeners()
    }

    private fun setupMealsProviderViewModel() {
        val mealsProviderViewModel: MealsProviderViewModel by viewModel{ parametersOf(getUserIdExtra()) }
        this.mealsProviderViewModel = mealsProviderViewModel

        this.mealsProviderViewModel.let {
            it.mealsLoadFailLiveData.observe(this, Observer {
                ToastManager.showToastShort(this, resources.getString(R.string.failed_to_load_meals))
            })

            it.mealsLoadSuccessLiveData.observe(this, Observer { list ->
                val mealAdapterModelList = list.map { meal ->
                    MealAdapterModel(
                        id = meal.id,
                        text = meal.params.text,
                        calories = meal.params.calories,
                        weight = meal.params.weight,
                        dayOfMonth = meal.filterParams.date.dayOfMonth,
                        month = meal.filterParams.date.month,
                        year = meal.filterParams.date.year,
                        from = meal.filterParams.time.from,
                        to = meal.filterParams.time.to,
                        isLimitExceeded = false
                    )
                }

                mealsAdapter.setItems(mealAdapterModelList)
            })

            it.noMealsFoundLiveData.observe(this, Observer {
                setupEmptyMealsUi()
            })

            it.getMeals()
        }
    }

    private fun setupDeleteMealViewModel() {
        val deleteMealViewModel: DeleteMealViewModel by viewModel{ parametersOf(getUserIdExtra()) }
        this.deleteMealViewModel = deleteMealViewModel

        this.deleteMealViewModel.let {
            it.deleteMealFailLiveData.observe(this, Observer {
                ToastManager.showToastShort(this, resources.getString(R.string.delete_meal_failed))
            })

            it.deleteMealSuccessLiveData.observe(this, Observer {
                ToastManager.showToastShort(this, resources.getString(R.string.delete_meal_succeed))
                mealsProviderViewModel.getMeals()
            })
        }
    }

    private fun setupUpdateDailyCaloriesViewModel() {
        val updateDailyCaloriesViewModel: UpdateDailyCaloriesViewModel by viewModel{ parametersOf(getUserIdExtra()) }
        this.updateDailyCaloriesViewModel = updateDailyCaloriesViewModel

        this.updateDailyCaloriesViewModel.let {
            it.updateCaloriesSucceedLiveData.observe(this, Observer {
                ToastManager.showToastShort(this, resources.getString(R.string.update_succeed))
            })

            it.updateCaloriesFailedLiveData.observe(this, Observer {
                ToastManager.showToastShort(this, resources.getString(R.string.update_failed))
            })
        }
    }

    private fun setupMealsRecyclerView() {
        mealsRecyclerView.layoutManager = LinearLayoutManager(this)
        mealsRecyclerView.adapter = mealsAdapter

        mealsAdapter.mealSelectedLiveData.observe(this, Observer {
            val mealSerializable = MealSerializable(
                id = it.id,
                text = it.text,
                calories = it.calories,
                weight = it.weight,
                year = it.year,
                month = it.month,
                dayOfMonth = it.dayOfMonth,
                from = it.from,
                to = it.to)

            ActivityNavigator.navigate(this, ViewMealActivity::class.java, MEAL_EXTRA, mealSerializable)
        })

        mealsAdapter.mealDeleteClickedLiveData.observe(this, Observer { mealId ->
            deleteMealViewModel.deleteMealClicked(mealId)
        })
    }

    private fun setupEmptyMealsUi() {
        noMealsToShowTextView.visibility = View.VISIBLE
    }

    private fun setupClickListeners() {
        addMealButton.setOnClickListener {
            ActivityNavigator.navigate(this, AddMealActivity::class.java)
        }
    }

    private fun getUserIdExtra() = intent.getStringExtra(USER_ID_KEY)

}