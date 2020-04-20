package com.example.inputofcalories.presentation.regularflow.home

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Window
import androidx.lifecycle.MutableLiveData
import com.example.inputofcalories.R
import kotlinx.android.synthetic.main.dialog_set_daily_calories.*

class SetDailyCaloriesDialog(context: Context): Dialog(context) {

    val dailyCaloriesLiveData = MutableLiveData<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        setTransparentBackground()

        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_set_daily_calories)

        setupClickListeners()
    }

    private fun setTransparentBackground() {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window?.run { setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT)) }
    }

    private fun setupClickListeners() {
        applyButton.setOnClickListener {
            val text = dailyCaloriesEditText.text.toString()
            if(text.isNotBlank()) {
                dailyCaloriesLiveData.value = text
                dismiss()
            } else {
                dailyCaloriesEditText.error = context.getString(R.string.please_set_daily_calories)
            }
        }
    }
}