package com.example.inputofcalories.presentation.base

import androidx.lifecycle.ViewModel
import org.koin.core.KoinComponent

open class BaseViewModel : ViewModel(), KoinComponent {

    open fun handleError(throwable: Throwable, requestCode: Int?) {}

}