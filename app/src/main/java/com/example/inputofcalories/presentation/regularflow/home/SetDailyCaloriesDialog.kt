package com.example.inputofcalories.presentation.regularflow.home

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import com.example.inputofcalories.R
import kotlinx.android.synthetic.main.dialog_set_daily_calories.*

class SetDailyCaloriesDialog(context: Context): Dialog(context) {

    val dailyCaloriesLiveData = MutableLiveData<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_set_daily_calories)

        setupClickListeners()
    }

    private fun setupClickListeners() {
        applyButton.setOnClickListener {
            dailyCaloriesLiveData.value = dailyCaloriesEditText.text.toString()
            dismiss()
        }
    }
}