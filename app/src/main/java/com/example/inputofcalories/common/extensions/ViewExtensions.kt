package com.example.inputofcalories.common.extensions

import android.graphics.Outline
import android.view.View
import android.view.ViewOutlineProvider
import com.example.inputofcalories.common.extensions.entity.*
import com.google.android.material.tabs.TabLayout

fun TabLayout.onItemSelected(result: (Int) -> Unit) {
    addOnTabSelectedListener(object: TabLayout.OnTabSelectedListener {
        override fun onTabReselected(tab: TabLayout.Tab?) {
        }

        override fun onTabUnselected(tab: TabLayout.Tab?) {
        }

        override fun onTabSelected(tab: TabLayout.Tab?) {
            tab?.run { result.invoke(position) }
        }
    })
}

fun View.makeCornersRounded(dir: RoundDir, radius: Float) {
    outlineProvider = object: ViewOutlineProvider() {
        override fun getOutline(view: View?, outline: Outline?) {
            outline?.run {
                when(dir) {
                    LEFT -> setRoundRect(0, 0, view!!.width + radius.toInt(), view.height, radius)
                    TOP -> setRoundRect(0, 0, view!!.width, (view.height + radius).toInt(), radius)
                    RIGHT -> setRoundRect(0 - radius.toInt(), 0, view!!.width, view.height, radius)
                    BOTTOM -> setRoundRect(0, 0 - radius.toInt(), view!!.width, view.height, radius)
                }
            }

        }
    }

    clipToOutline = true
}