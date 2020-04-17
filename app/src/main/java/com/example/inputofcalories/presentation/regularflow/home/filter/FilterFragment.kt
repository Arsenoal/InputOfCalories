package com.example.inputofcalories.presentation.regularflow.home.filter

import android.view.View
import com.example.inputofcalories.R
import com.example.inputofcalories.common.extensions.entity.BOTTOM
import com.example.inputofcalories.common.extensions.entity.LEFT
import com.example.inputofcalories.common.extensions.entity.RIGHT
import com.example.inputofcalories.common.extensions.entity.TOP
import com.example.inputofcalories.common.extensions.makeCornersRounded
import com.example.inputofcalories.presentation.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_filter.*

class FilterFragment: BaseFragment() {

    override fun getLayoutId() = R.layout.fragment_filter

    override fun onLayoutReady(view: View) {
        setupImageCorners()
    }

    private fun setupImageCorners() {
        breakfastImageView.makeCornersRounded(LEFT, 22f)
        snackImageView.makeCornersRounded(RIGHT, 22f)
        lunchImageView.makeCornersRounded(LEFT, 22f)
        dinnerImageView.makeCornersRounded(RIGHT, 22f)
    }

    companion object {
        fun newInstance() = FilterFragment()
    }
}