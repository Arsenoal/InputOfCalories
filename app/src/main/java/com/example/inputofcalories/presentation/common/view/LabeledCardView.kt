package com.example.inputofcalories.presentation.common.view

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import androidx.cardview.R
import androidx.cardview.widget.CardView

class LabeledCardView(
    context: Context,
    attrs: AttributeSet? = null,
    @SuppressLint("PrivateResource")
    defStyle: Int = R.attr.cardViewStyle): CardView(context, attrs, defStyle) {

    sealed class Type {
        object BREAKFAST: Type()
        object LUNCH: Type()
        object SNACK: Type()
        object DINNER: Type()
    }

    lateinit var type: Type


}