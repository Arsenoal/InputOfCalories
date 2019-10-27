package com.example.inputofcalories.presentation.regularflow.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageButton
import androidx.appcompat.widget.AppCompatTextView
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.example.inputofcalories.R
import com.example.inputofcalories.entity.presentation.regular.Meal
import java.util.*

class MealsRecyclerAdapter(
    val meals: MutableList<Meal> = mutableListOf()
): RecyclerView.Adapter<MealsRecyclerAdapter.MealViewHolder>() {

    private lateinit var layoutInflater: LayoutInflater

    val mealSelectedLiveData: MutableLiveData<Meal> = MutableLiveData()

    val mealDeleteClickedLiveData: MutableLiveData<String> = MutableLiveData()

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        layoutInflater = LayoutInflater.from(recyclerView.context)
    }

    class MealViewHolder(view: View): RecyclerView.ViewHolder(view) {
        private val mealTextTextView: AppCompatTextView = view.findViewById(R.id.mealTextTextView)
        private val mealCaloriesTextView: AppCompatTextView = view.findViewById(R.id.mealCaloriesTextView)
        private val mealWeightTextView: AppCompatTextView = view.findViewById(R.id.mealWeightTextView)
        private val dateTextView: AppCompatTextView = view.findViewById(R.id.dateTextView)
        val deleteMealButton: AppCompatImageButton = view.findViewById(R.id.deleteMealButton)

        fun bind(meal: Meal) {
            mealTextTextView.text = meal.params.text
            mealCaloriesTextView.text = meal.params.calories
            mealWeightTextView.text = String.format(Locale.ENGLISH, "%s g", meal.params.weight)
            dateTextView.text = String.format(Locale.ENGLISH, "%s/%s %s: %s, %s: %s",
                meal.filterParams.date.month,
                meal.filterParams.date.dayOfMonth,
                itemView.context.resources.getString(R.string.from),
                meal.filterParams.time.from,
                itemView.context.resources.getString(R.string.to),
                meal.filterParams.time.to)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MealViewHolder {
        return MealViewHolder(
            layoutInflater.inflate(R.layout.meal_recycler_item, parent, false)
        )
    }

    override fun getItemCount() = meals.size

    fun addItems(items: List<Meal>) {
        val initialSize = meals.size
        meals.addAll(items)
        notifyItemRangeInserted(initialSize, items.size)
    }

    fun setItems(items: List<Meal>) {
        meals.clear()
        meals.addAll(items)
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: MealViewHolder, position: Int) {
        val meal = meals[position]

        holder.bind(meal)

        holder.itemView.setOnClickListener {
            mealSelectedLiveData.value = meal
        }

        holder.deleteMealButton.setOnClickListener {
            mealDeleteClickedLiveData.value = meal.id
        }
    }
}