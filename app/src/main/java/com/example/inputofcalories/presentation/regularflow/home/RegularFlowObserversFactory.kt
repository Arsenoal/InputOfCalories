package com.example.inputofcalories.presentation.regularflow.home

import androidx.lifecycle.Observer
import com.example.inputofcalories.entity.presentation.regular.toAdapterModel
import com.example.inputofcalories.presentation.regularflow.model.entity.*
import com.example.inputofcalories.presentation.regularflow.model.entity.DeleteMealState.*

class RegularFlowObserversFactory(activity: RegularUserHomeActivity, mealsAdapter: MealsRecyclerAdapter) {

    private val loadMealsObserver = Observer<GetMealsState> { state ->
        when(state) {
            is GetMealsState.GetMealsSucceed -> {
                activity.hideProgress()
                mealsAdapter.setItems(state.meals.map { meal -> meal.toAdapterModel() })
            }
            GetMealsState.NoMealsToShow -> {
                activity.run {
                    hideProgress()
                    loadMore()
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

    private val loadMoreObserver = Observer<GetMealsState> { state ->
        when(state) {
            is GetMealsState.GetMealsSucceed -> {
                activity.hideProgress()
                mealsAdapter.addItems(state.meals.map { meal -> meal.toAdapterModel() })
            }
            GetMealsState.NoMealsToShow -> { }
            GetMealsState.GetMealsFailed -> { }
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
                //mealsAdapter.markOnLimitExceeded()
            }
            DailyCaloriesLimitState.DailyLimitNotExceeded -> {
                activity.hideProgress()
                //mealsAdapter.markOnLimitNotExceeded()
            }
        }
    }

    private val updateDailyCaloriesObserver = Observer<DailyCaloriesState> { state ->
        when(state) {
            DailyCaloriesState.DailyCaloriesSaveSucceed -> { activity.hideProgress() }
            DailyCaloriesState.DailyCaloriesSaveFailed -> { activity.hideProgress() }
        }
    }

    private val deleteMealObserver = Observer<DeleteMealState> { state ->
        when(state) {
            is DeleteMealSucceed -> {
                activity.run {
                    deleteMeal(state.position)
                    hideProgress()
                }
            }
            DeleteMealFailed -> { activity.hideProgress() }
        }
    }

    @Suppress("UNCHECKED_CAST")
    fun <T> get(key: ObserverKey): Observer<in T> = when(key) {
        ObserverKey.GetMealsObserver -> loadMealsObserver as Observer<in T>
        ObserverKey.GetMoreMealsObserver -> loadMoreObserver as Observer<in T>
        ObserverKey.GetMealsFilteredObserver -> getMealsFilteredObserver as Observer<in T>
        ObserverKey.CheckDailyLimitObserver -> checkDailyCaloriesObserver as Observer<in T>
        ObserverKey.DeleteMealObserver -> deleteMealObserver as Observer<in T>
        ObserverKey.UpdateDailyCaloriesObserver -> updateDailyCaloriesObserver as Observer<in T>
    }

    sealed class ObserverKey {
        object GetMealsObserver: ObserverKey()
        object GetMoreMealsObserver: ObserverKey()
        object GetMealsFilteredObserver: ObserverKey()
        object CheckDailyLimitObserver: ObserverKey()
        object DeleteMealObserver: ObserverKey()
        object UpdateDailyCaloriesObserver: ObserverKey()
    }

}