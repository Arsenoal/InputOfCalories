package com.example.inputofcalories.presentation.adminflow.usermeals

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.inputofcalories.R
import com.example.inputofcalories.presentation.base.BaseActivity
import com.example.inputofcalories.presentation.common.ToastManager
import com.example.inputofcalories.presentation.commonextras.ExtraKeys.MEAL_EXTRA
import com.example.inputofcalories.presentation.commonextras.ExtraKeys.USER_ID_KEY
import com.example.inputofcalories.presentation.navigation.ActivityNavigator
import com.example.inputofcalories.presentation.regularflow.addmeal.AddMealActivity
import com.example.inputofcalories.presentation.regularflow.home.MealsRecyclerAdapter
import com.example.inputofcalories.presentation.regularflow.home.model.toMealSerializable
import com.example.inputofcalories.presentation.regularflow.home.viewmodel.*
import com.example.inputofcalories.presentation.regularflow.viewmeal.ViewMealActivity
import kotlinx.android.synthetic.main.activity_regular_user_home.*
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

import com.example.inputofcalories.presentation.adminflow.usermeals.UserMealsFlowObserversFactory.ObservableKey
import com.example.inputofcalories.presentation.regularflow.home.model.DeleteParams

class UserMealsActivity: BaseActivity() {

    private val mealsViewModel: MealsViewModel by viewModel()

    private lateinit var observersFactory: UserMealsFlowObserversFactory

    private val mealsAdapter = MealsRecyclerAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_regular_user_home)

        setupObserverFactory()

        loadMeals()

        setupMealsRecyclerView()

        setupClickListeners()
    }

    private fun setupObserverFactory() {
        observersFactory = UserMealsFlowObserversFactory(this, mealsAdapter)
    }

    fun loadMeals() {
        mealsViewModel.getMeals().observe(this, observersFactory.get(ObservableKey.GetMealsObservable))
    }

    fun deleteMeal(position: Int) {
        mealsAdapter.deleteItem(position)
    }

    private fun setupMealsRecyclerView() {
        mealsRecyclerView.layoutManager = LinearLayoutManager(this)
        mealsRecyclerView.adapter = mealsAdapter

        mealsAdapter.mealSelectedLiveData.observe(this, Observer { mealsAdapterModel ->

            val mealSerializable = mealsAdapterModel.toMealSerializable()

            ActivityNavigator.navigate(this, ViewMealActivity::class.java, MEAL_EXTRA, mealSerializable)
        })

        mealsAdapter.mealDeleteClickedLiveData.observe(this, Observer { deleteParams ->
            mealsViewModel.deleteMeal(deleteParams).observe(this, observersFactory.get(ObservableKey.DeleteMealObservable))
        })
    }

    fun showEmptyMealsUi() { noMealsToShowTextView.visibility = View.VISIBLE }

    fun showReloadMealsOption() {

    }

    private fun setupClickListeners() {
        addMealButton.setOnClickListener { ActivityNavigator.navigate(this, AddMealActivity::class.java) }
    }

}