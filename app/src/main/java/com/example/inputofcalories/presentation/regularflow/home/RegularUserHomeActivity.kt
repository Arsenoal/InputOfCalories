package com.example.inputofcalories.presentation.regularflow.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View.VISIBLE
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.inputofcalories.R
import com.example.inputofcalories.presentation.ToastManager
import com.example.inputofcalories.presentation.navigation.ActivityNavigator
import com.example.inputofcalories.presentation.regularflow.addmeal.AddMealActivity
import com.example.inputofcalories.presentation.regularflow.model.MealSerializable
import com.example.inputofcalories.presentation.regularflow.viewmeal.MEAL_EXTRA
import com.example.inputofcalories.presentation.regularflow.viewmeal.ViewMealActivity
import kotlinx.android.synthetic.main.activity_regular_user_home.*
import kotlinx.android.synthetic.main.activity_regular_user_home.addMealButton
import kotlinx.android.synthetic.main.activity_regular_user_home.toolbar
import org.koin.android.viewmodel.ext.android.viewModel

class RegularUserHomeActivity : AppCompatActivity() {

    private val mealsProviderViewModel: MealsProviderViewModel by viewModel()

    private val deleteMealViewModel: DeleteMealViewModel by viewModel()

    private val mealsAdapter = MealsRecyclerAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_regular_user_home)

        setupViewModel()

        setupDeleteMealViewModel()

        setupMealsRecyclerView()

        setupToolBar()

        setupClickListeners()

        loadMealsList()
    }

    private fun setupViewModel() {
        mealsProviderViewModel.mealsLoadFailLiveData.observe(this, Observer {
            ToastManager.showToastShort(this, "failed to load meals")
        })

        mealsProviderViewModel.mealsLoadSuccessLiveData.observe(this, Observer { list ->
            mealsAdapter.setItems(list)
        })

        mealsProviderViewModel.noMealsFoundLiveData.observe(this, Observer {
            setupEmptyMealsUi()
        })
    }

    private fun setupDeleteMealViewModel() {
        deleteMealViewModel.deleteMealFailLiveData.observe(this, Observer {
            ToastManager.showToastShort(this, "delete meal failed")
        })

        deleteMealViewModel.deleteMealSuccessLiveData.observe(this, Observer {
            ToastManager.showToastShort(this, "delete meal succeed")
            mealsProviderViewModel.getMeals()
        })
    }

    private fun setupEmptyMealsUi() {
        noMealsToShowTextView.visibility = VISIBLE
    }

    private fun loadMealsList() {
        mealsProviderViewModel.getMeals()
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
                dialog.applyFilterLiveData.observe(this, Observer {
                    mealsProviderViewModel.getMealsFiltered(it)
                })

                dialog.show()
            }
        }

        return true
    }
}
