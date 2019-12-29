package com.example.inputofcalories.presentation.customview

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatEditText
import com.example.inputofcalories.R

//TODO
class ErrorHandlerEditText(context: Context, attrs: AttributeSet?, defStyleAttr: Int): AppCompatEditText(context, attrs, defStyleAttr) {

    constructor(context: Context): this(context = context, attrs = null, defStyleAttr = R.attr.editTextStyle)

    constructor(context: Context, attrs: AttributeSet): this(context = context, attrs = attrs, defStyleAttr = R.attr.editTextStyle)

    fun onError(drawable: Drawable, textColor: Int?) {
        textColor?.let {
            setTextColor(it)
        }

        background = drawable
        invalidate()
    }

    fun onError(drawableResId: Int, textColor: Int?) {
        textColor?.let {
            setTextColor(it)
        }

        setBackgroundResource(drawableResId)
        invalidate()
    }
}