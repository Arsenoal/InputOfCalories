package com.example.inputofcalories.presentation.regularflow.model.entity

import com.example.inputofcalories.entity.presentation.regular.Meal

sealed class GetMealsState {

    class GetMealsSucceed(val meals: List<Meal>) : GetMealsState()

    object GetMealsFailed : GetMealsState()

    object NoMealsToShow : GetMealsState()
}

sealed class GetMealsFilteredState {
    class GetMealsFilteredSucceed(val meals: List<Meal>) : GetMealsFilteredState()

    object GetMealsFilteredFailed : GetMealsFilteredState()
}

sealed class DeleteMealState {

    object DeleteMealSucceed : DeleteMealState()

    object DeleteMealFailed : DeleteMealState()
}

sealed class AddMealState {

    object AddMealSucceed : AddMealState()

    object AddMealFailed : AddMealState()
}

sealed class EditMealState {

    object EditMealSucceed : EditMealState()

    object EditMealFailed : EditMealState()
}