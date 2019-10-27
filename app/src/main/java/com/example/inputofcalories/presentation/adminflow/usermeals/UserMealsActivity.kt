package com.example.inputofcalories.presentation.adminflow.usermeals

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.inputofcalories.R
import com.example.inputofcalories.presentation.ToastManager
import com.example.inputofcalories.presentation.navigation.ActivityNavigator
import com.example.inputofcalories.presentation.regularflow.home.DeleteMealViewModel
import com.example.inputofcalories.presentation.regularflow.home.MealsProviderViewModel
import com.example.inputofcalories.presentation.regularflow.home.MealsRecyclerAdapter
import com.example.inputofcalories.presentation.regularflow.home.UpdateDailyCaloriesViewModel
import com.example.inputofcalories.presentation.regularflow.model.MealSerializable
import com.example.inputofcalories.presentation.regularflow.viewmeal.MEAL_EXTRA
import com.example.inputofcalories.presentation.regularflow.viewmeal.ViewMealActivity
import kotlinx.android.synthetic.main.activity_regular_user_home.*
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

const val USER_ID_KEY = "user_id_key"

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
    }

    private fun setupMealsProviderViewModel() {
        val mealsProviderViewModel: MealsProviderViewModel by viewModel{ parametersOf(getUserIdExtra()) }
        this.mealsProviderViewModel = mealsProviderViewModel

        this.mealsProviderViewModel.run {
            mealsLoadFailLiveData.observe(this@UserMealsActivity, Observer {
                ToastManager.showToastShort(this@UserMealsActivity, "failed to load meals")
            })

            mealsLoadSuccessLiveData.observe(this@UserMealsActivity, Observer { list ->
                mealsAdapter.setItems(list)
            })

            noMealsFoundLiveData.observe(this@UserMealsActivity, Observer {
                setupEmptyMealsUi()
            })

            getMeals()
        }
    }

    private fun setupDeleteMealViewModel() {
        val deleteMealViewModel: DeleteMealViewModel by viewModel{ parametersOf(getUserIdExtra()) }
        this.deleteMealViewModel = deleteMealViewModel

        this.deleteMealViewModel.run {
            deleteMealFailLiveData.observe(this@UserMealsActivity, Observer {
                ToastManager.showToastShort(this@UserMealsActivity, "delete meal failed")
            })

            deleteMealSuccessLiveData.observe(this@UserMealsActivity, Observer {
                ToastManager.showToastShort(this@UserMealsActivity, "delete meal succeed")
                mealsProviderViewModel.getMeals()
            })
        }
    }

    private fun setupUpdateDailyCaloriesViewModel() {
        val updateDailyCaloriesViewModel: UpdateDailyCaloriesViewModel by viewModel{ parametersOf(getUserIdExtra()) }
        this.updateDailyCaloriesViewModel = updateDailyCaloriesViewModel

        this.updateDailyCaloriesViewModel.run {
            updateCaloriesSucceedLiveData.observe(this@UserMealsActivity, Observer { message ->
                ToastManager.showToastShort(this@UserMealsActivity, message.message)
            })

            updateCaloriesFailedLiveData.observe(this@UserMealsActivity, Observer { message ->
                ToastManager.showToastShort(this@UserMealsActivity, message.message)
            })
        }
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
            deleteMealViewModel.deleteMealClicked(mealId)
        })
    }

    private fun setupEmptyMealsUi() {
        noMealsToShowTextView.visibility = View.VISIBLE
    }

    private fun getUserIdExtra() = intent.getStringExtra(USER_ID_KEY)

}