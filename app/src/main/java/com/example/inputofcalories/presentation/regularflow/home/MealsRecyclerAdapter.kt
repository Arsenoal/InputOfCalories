package com.example.inputofcalories.presentation.regularflow.home

import android.view.LayoutInflater
import android.view.View
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.appcompat.widget.AppCompatImageButton
import androidx.appcompat.widget.AppCompatTextView
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.example.inputofcalories.R
import com.example.inputofcalories.entity.presentation.regular.Meal
import com.example.inputofcalories.presentation.regularflow.home.model.MealAdapterModel
import java.util.*

class MealsRecyclerAdapter(
    val meals: MutableList<MealAdapterModel> = mutableListOf()
): RecyclerView.Adapter<MealsRecyclerAdapter.MealViewHolder>() {

    private lateinit var layoutInflater: LayoutInflater

    val mealSelectedLiveData: MutableLiveData<MealAdapterModel> = MutableLiveData()

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
        private val limitMarker: FrameLayout = view.findViewById(R.id.limitExceededColorView)
        val deleteMealButton: AppCompatImageButton = view.findViewById(R.id.deleteMealButton)

        fun bind(mealAdapterModel: MealAdapterModel) {
            mealTextTextView.text = mealAdapterModel.text
            mealCaloriesTextView.text = mealAdapterModel.calories
            mealWeightTextView.text = String.format(Locale.ENGLISH, "%s g", mealAdapterModel.weight)
            dateTextView.text = String.format(Locale.ENGLISH, "%s/%s %s: %s, %s: %s",
                mealAdapterModel.month,
                mealAdapterModel.dayOfMonth,
                itemView.context.resources.getString(R.string.from),
                mealAdapterModel.from,
                itemView.context.resources.getString(R.string.to),
                mealAdapterModel.to)

            if(mealAdapterModel.isLimitExceeded) limitMarker.background = itemView.context.getDrawable(R.drawable.background_circle_primary_dark)
            else limitMarker.background = itemView.context.getDrawable(R.drawable.background_circle_accent)

            limitMarker.visibility = VISIBLE
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MealViewHolder {
        return MealViewHolder(
            layoutInflater.inflate(R.layout.meal_recycler_item, parent, false)
        )
    }

    override fun getItemCount() = meals.size

    fun setItems(items: List<MealAdapterModel>) {
        meals.clear()
        meals.addAll(items)
        notifyDataSetChanged()
    }

    fun markOnLimitExceeded() {
        meals.forEach { it.isLimitExceeded = true }
        notifyDataSetChanged()
    }

    fun markOnLimitNotExceeded() {
        meals.forEach { it.isLimitExceeded = false }
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: MealViewHolder, position: Int) {
        val mealAdapterModel = meals[position]

        holder.bind(mealAdapterModel)

        holder.itemView.setOnClickListener {
            mealSelectedLiveData.value = mealAdapterModel
        }

        holder.deleteMealButton.setOnClickListener {
            mealDeleteClickedLiveData.value = mealAdapterModel.id
        }
    }
}