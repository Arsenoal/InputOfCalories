package com.example.inputofcalories.presentation.regularflow.addmeal.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.example.inputofcalories.R
import com.example.inputofcalories.entity.presentation.regular.BreakfastTime
import com.example.inputofcalories.entity.presentation.regular.DinnerTime
import com.example.inputofcalories.entity.presentation.regular.LunchTime
import com.example.inputofcalories.entity.presentation.regular.SnackTime
import com.example.inputofcalories.presentation.regularflow.addmeal.adapter.model.MealTimePickerParams

class MealTimePickerAdapter(
    private val params: List<MealTimePickerParams>
): RecyclerView.Adapter<MealTimePickerAdapter.ViewHolder>() {

    private lateinit var layoutInflater: LayoutInflater

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)

        layoutInflater = LayoutInflater.from(recyclerView.context)
    }

    class ViewHolder(val view: View): RecyclerView.ViewHolder(view) {
        private val mealTimeTextView = view.findViewById<AppCompatTextView>(R.id.mealTimeTextView)

        fun bind(param: MealTimePickerParams) {
            val resources = view.context.resources
            when(param.mealTime) {
                BreakfastTime -> { mealTimeTextView.text = resources.getString(R.string.breakfast) }
                SnackTime -> { mealTimeTextView.text = resources.getString(R.string.snack) }
                LunchTime -> { mealTimeTextView.text = resources.getString(R.string.lunch) }
                DinnerTime -> { mealTimeTextView.text = resources.getString(R.string.dinner) }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(layoutInflater.inflate(R.layout.meal_time_recycler_item, parent, false))
    }

    override fun getItemCount() = params.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val param = params[position]

        holder.bind(param)
    }
}