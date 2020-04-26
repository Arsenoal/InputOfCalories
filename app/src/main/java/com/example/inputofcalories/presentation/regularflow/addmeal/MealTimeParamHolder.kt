package com.example.inputofcalories.presentation.regularflow.addmeal

import androidx.lifecycle.MutableLiveData
import com.example.inputofcalories.entity.presentation.regular.MealTimeParams

interface MealTimeParamHolder {
    fun getMealTimeLiveData(): MutableLiveData<MealTimeParams>
}