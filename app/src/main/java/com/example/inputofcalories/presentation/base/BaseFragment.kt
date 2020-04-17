package com.example.inputofcalories.presentation.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment

abstract class BaseFragment: Fragment() {

    @LayoutRes abstract fun getLayoutId(): Int

    abstract fun onLayoutReady(view: View)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(getLayoutId(), container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        onLayoutReady(view)

        activity?.run {

            softInputModeFlags().takeIf { it.isNotEmpty() }?.let { flags ->
                var mode = flags[0]
                flags.forEach{ mode = mode or it }

                window.setSoftInputMode(mode)
            }
        }
    }

    open fun softInputModeFlags() = listOf<Int>()
}