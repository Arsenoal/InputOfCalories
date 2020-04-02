package com.example.inputofcalories.presentation.regularflow.home

import androidx.lifecycle.Observer
import com.example.inputofcalories.entity.presentation.regular.toAdapterModel
import com.example.inputofcalories.presentation.regularflow.model.entity.*
import com.example.inputofcalories.presentation.regularflow.model.entity.DeleteMealState.*

class RegularFlowObserversFactory(activity: RegularUserHomeActivity, mealsAdapter: MealsRecyclerAdapter) {

    private val getMealsObserver = Observer<GetMealsState> { state ->
        when(state) {
            is GetMealsState.GetMealsSucceed -> {
                activity.hideProgress()
                mealsAdapter.setItems(state.meals.map { meal -> meal.toAdapterModel() })
            }
            GetMealsState.NoMealsToShow -> {
                activity.run {
                    hideProgress()
                    showEmptyMealsUi()
                }
            }
            GetMealsState.GetMealsFailed -> {
                activity.run {
                    hideProgress()
                    showReloadMealsOption()
                }
            }
        }
    }

    private val getMealsFilteredObserver = Observer<GetMealsFilteredState> { state ->
        when(state) {
            is GetMealsFilteredState.GetMealsFilteredSucceed -> {
                activity.hideProgress()
                val mealAdapterModelList = state.meals.map { meal -> meal.toAdapterModel() }

                mealsAdapter.setItems(mealAdapterModelList)
            }
            GetMealsFilteredState.GetMealsFilteredFailed -> {
                activity.hideProgress()
            }
        }
    }

    private val checkDailyCaloriesObserver = Observer<DailyCaloriesLimitState> { state ->
        when(state) {
            DailyCaloriesLimitState.DailyLimitExceeded -> {
                activity.hideProgress()
                mealsAdapter.markOnLimitExceeded()
            }
            DailyCaloriesLimitState.DailyLimitNotExceeded -> {
                activity.hideProgress()
                mealsAdapter.markOnLimitNotExceeded()
            }
        }
    }

    private val updateDailyCaloriesObserver = Observer<DailyCaloriesLimitState> { state ->
        when(state) {
            DailyCaloriesLimitState.DailyLimitExceeded -> { activity.hideProgress() }
            DailyCaloriesLimitState.DailyLimitNotExceeded -> { activity.hideProgress() }
        }
    }

    private val deleteMealObserver = Observer<DeleteMealState> { state ->
        when(state) {
            DeleteMealSucceed -> {
                activity.run {
                    showProgress()
                    loadMeals()
                }
            }
            DeleteMealFailed -> { activity.hideProgress() }
        }
    }

    @Suppress("UNCHECKED_CAST")
    fun <T> get(key: ObservableKey): Observer<in T> = when(key) {
        ObservableKey.GetMealsObserver -> getMealsObserver as Observer<in T>
        ObservableKey.GetMealsFilteredObserver -> getMealsFilteredObserver as Observer<in T>
        ObservableKey.CheckDailyLimitObserver -> checkDailyCaloriesObserver as Observer<in T>
        ObservableKey.DeleteMealObserver -> deleteMealObserver as Observer<in T>
        ObservableKey.UpdateDailyCaloriesObserver -> updateDailyCaloriesObserver as Observer<in T>
    }

    sealed class ObservableKey {
        object GetMealsObserver: ObservableKey()
        object GetMealsFilteredObserver: ObservableKey()
        object CheckDailyLimitObserver: ObservableKey()
        object DeleteMealObserver: ObservableKey()
        object UpdateDailyCaloriesObserver: ObservableKey()
    }

}