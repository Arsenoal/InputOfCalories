package com.example.inputofcalories.common.extensions

import com.google.android.material.tabs.TabLayout

fun TabLayout.onItemSelected(result: (Int) -> Unit) {
    addOnTabSelectedListener(object: TabLayout.BaseOnTabSelectedListener<TabLayout.Tab> {
        override fun onTabReselected(p0: TabLayout.Tab?) {}

        override fun onTabUnselected(p0: TabLayout.Tab?) {}

        override fun onTabSelected(p0: TabLayout.Tab?) {
            p0?.run {
                result.invoke(p0.position)
            }
        }
    })
}