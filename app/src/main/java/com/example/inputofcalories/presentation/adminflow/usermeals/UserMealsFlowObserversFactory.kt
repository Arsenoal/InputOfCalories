package com.example.inputofcalories.presentation.adminflow.usermeals

import androidx.lifecycle.Observer
import com.example.inputofcalories.entity.presentation.regular.toAdapterModel
import com.example.inputofcalories.presentation.regularflow.home.MealsRecyclerAdapter
import com.example.inputofcalories.presentation.regularflow.model.entity.DeleteMealState
import com.example.inputofcalories.presentation.regularflow.model.entity.GetMealsState

class UserMealsFlowObserversFactory(activity: UserMealsActivity, adapter: MealsRecyclerAdapter) {

    private val getMealsObserver = Observer<GetMealsState> { state ->
        when(state) {
            is GetMealsState.GetMealsSucceed -> { adapter.setItems(state.meals.map { it.toAdapterModel() }) }
            GetMealsState.GetMealsFailed -> { activity.showReloadMealsOption() }
            GetMealsState.NoMealsToShow -> { activity.showEmptyMealsUi() }
        }
    }

    private val deleteMealObserver = Observer<DeleteMealState> { state ->
        when(state) {
            DeleteMealState.DeleteMealFailed -> {}
            DeleteMealState.DeleteMealSucceed -> { activity.loadMeals() }
        }
    }

    @Suppress("UNCHECKED_CAST")
    fun <T> get(key: ObservableKey): Observer<in T> = when(key) {
        ObservableKey.GetMealsObservable -> getMealsObserver as Observer<in T>
        ObservableKey.DeleteMealObservable -> deleteMealObserver as Observer<in T>
    }

    sealed class ObservableKey {
        object GetMealsObservable: ObservableKey()
        object DeleteMealObservable: ObservableKey()
    }
}