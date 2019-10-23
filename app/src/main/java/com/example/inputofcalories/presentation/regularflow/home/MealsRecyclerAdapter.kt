package com.example.inputofcalories.presentation.regularflow.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.example.inputofcalories.R
import com.example.inputofcalories.entity.presentation.regular.Meal

class MealsRecyclerAdapter(
    val meals: MutableList<Meal> = mutableListOf()
): RecyclerView.Adapter<MealsRecyclerAdapter.MealViewHolder>() {

    lateinit var layoutInflater: LayoutInflater

    val mealSelectedLiveData: MutableLiveData<Meal> = MutableLiveData()

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        layoutInflater = LayoutInflater.from(recyclerView.context)
    }

    class MealViewHolder(view: View): RecyclerView.ViewHolder(view) {
        private val mealTextTextView = view.findViewById<AppCompatTextView>(R.id.mealTextTextView)
        private val mealCaloriesTextView = view.findViewById<AppCompatTextView>(R.id.mealCaloriesTextView)
        private val mealWeightTextView = view.findViewById<AppCompatTextView>(R.id.mealWeightTextView)
        private val dateTextView = view.findViewById<AppCompatTextView>(R.id.dateTextView)

        fun bind(meal: Meal) {
            mealTextTextView.text = meal.params.text
            mealCaloriesTextView.text = meal.params.calories
            mealWeightTextView.text = "${meal.params.weight} g"
            dateTextView.text = "${meal.filterParams.date.month}/${meal.filterParams.date.dayOfMonth} from: ${meal.filterParams.time.from}, to: ${meal.filterParams.time.to}"
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
    }
}