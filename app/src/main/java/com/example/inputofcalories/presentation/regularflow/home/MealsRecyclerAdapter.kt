package com.example.inputofcalories.presentation.regularflow.home

import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.example.inputofcalories.R
import com.example.inputofcalories.presentation.regularflow.home.model.DeleteParams
import com.example.inputofcalories.presentation.regularflow.home.model.MealAdapterModel
import com.example.inputofcalories.presentation.regularflow.home.model.toDeleteParams
import java.util.*

class MealsRecyclerAdapter(
    private val meals: MutableList<MealAdapterModel> = mutableListOf()
): RecyclerView.Adapter<MealsRecyclerAdapter.MealViewHolder>() {

    private lateinit var layoutInflater: LayoutInflater

    val mealSelectedLiveData: MutableLiveData<MealAdapterModel> = MutableLiveData()

    val mealDeleteClickedLiveData: MutableLiveData<DeleteParams> = MutableLiveData()

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        layoutInflater = LayoutInflater.from(recyclerView.context)
    }

    @Suppress("DEPRECATION")
    class MealViewHolder(view: View): RecyclerView.ViewHolder(view) {
        private val rootView: FrameLayout = view.findViewById(R.id.rootView)
        private val mealTextTextView: AppCompatTextView = view.findViewById(R.id.mealTextTextView)
        private val mealCaloriesTextView: AppCompatTextView = view.findViewById(R.id.mealCaloriesTextView)
        private val mealWeightTextView: AppCompatTextView = view.findViewById(R.id.mealWeightTextView)
        val deleteMealButton: AppCompatImageView = view.findViewById(R.id.deleteMealButton)

        fun bind(mealAdapterModel: MealAdapterModel) {
            val resources = rootView.context.resources

            mealTextTextView.text = mealAdapterModel.text
            mealCaloriesTextView.text = String.format(Locale.ENGLISH, "%s%s",
                Html.fromHtml(resources.getString(R.string.meal_calories_placeholder)), mealAdapterModel.calories)
            mealWeightTextView.text =
                String.format(Locale.ENGLISH, "%s%sg",
                    Html.fromHtml(resources.getString(R.string.meal_weight_placeholder)), mealAdapterModel.weight)

            if(mealAdapterModel.isLimitExceeded) { rootView.setBackgroundResource(R.drawable.limit_exceeded_bg) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MealViewHolder {
        return MealViewHolder(layoutInflater.inflate(R.layout.meal_recycler_item, parent, false))
    }

    override fun getItemCount() = meals.size

    fun setItems(items: List<MealAdapterModel>) {
        meals.clear()
        meals.addAll(items)
        notifyDataSetChanged()
    }

    fun addItems(items: List<MealAdapterModel>) {
        val initialSize = meals.size
        meals.addAll(items)
        notifyItemRangeInserted(initialSize, items.size)
    }

    fun deleteItem(position: Int) {
        meals.removeAt(position)
        notifyItemRemoved(position)
    }

    @Suppress("UNUSED")
    fun markOnLimitExceeded(meals: List<MealAdapterModel>) = with(meals) {
        forEach { it.isLimitExceeded = true }
        notifyDataSetChanged()
    }

    @Suppress("UNUSED")
    fun markOnLimitNotExceeded(meals: List<MealAdapterModel>) = with(meals) {
        forEach { it.isLimitExceeded = false }
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: MealViewHolder, position: Int) = with(holder) {
        val mealAdapterModel = meals[position]

        bind(mealAdapterModel)

        itemView.setOnClickListener { mealSelectedLiveData.value = mealAdapterModel }

        deleteMealButton.setOnClickListener { mealDeleteClickedLiveData.value = mealAdapterModel.toDeleteParams(position) }
    }
}