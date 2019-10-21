package com.example.inputofcalories.presentation.regularflow

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View.VISIBLE
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.inputofcalories.R
import com.example.inputofcalories.presentation.navigation.ActivityNavigator
import com.example.inputofcalories.presentation.regularflow.addmeal.AddMealActivity
import com.example.inputofcalories.presentation.regularflow.model.MealSerializable
import com.example.inputofcalories.presentation.regularflow.viewmeal.MEAL_EXTRA
import com.example.inputofcalories.presentation.regularflow.viewmeal.ViewMealActivity
import kotlinx.android.synthetic.main.activity_regular_user_home.*
import org.koin.android.ext.android.inject

class RegularUserHomeActivity : AppCompatActivity() {

    private val mealsProviderViewModel: MealsProviderViewModel by inject()

    private val mealsAdapter = MealsRecyclerAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_regular_user_home)

        setupViewModel()

        setupClickListeners()

        setupMealsRecyclerView()

        loadMealsList()
    }

    private fun setupViewModel() {
        mealsProviderViewModel.mealsLoadFailLiveData.observe(this, Observer {  })

        mealsProviderViewModel.mealsLoadSuccessLiveData.observe(this, Observer { list ->
            mealsAdapter.addItems(list)
        })

        mealsProviderViewModel.noMealsFoundLiveData.observe(this, Observer {
            setupEmptyMealsUi()
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
            //TODO navigate to view meal page(ehm, create that page first I guess)
            val mealSerializable = MealSerializable(
                it.id,
                it.params.text,
                it.params.calories,
                it.params.weight
            )

            ActivityNavigator.navigate(this, ViewMealActivity::class.java, MEAL_EXTRA, mealSerializable)
        })
    }

    private fun setupClickListeners() {
        addMealButton.setOnClickListener {
            ActivityNavigator.navigate(this, AddMealActivity::class.java)
        }
    }
}
