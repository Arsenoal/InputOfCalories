package com.example.inputofcalories.presentation.regularflow.home

import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.example.inputofcalories.R
import com.example.inputofcalories.presentation.regularflow.home.model.DeleteParams
import com.example.inputofcalories.presentation.regularflow.home.model.MealAdapterModel
import com.example.inputofcalories.presentation.regularflow.home.model.containsId
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

    class MealViewHolder(view: View): RecyclerView.ViewHolder(view) {
        private val rootView: FrameLayout = view.findViewById(R.id.rootView)
        private val mealTextTextView: AppCompatTextView = view.findViewById(R.id.mealTextTextView)
        private val mealCaloriesTextView: AppCompatTextView = view.findViewById(R.id.mealCaloriesTextView)
        private val mealWeightTextView: AppCompatTextView = view.findViewById(R.id.mealWeightTextView)
        val deleteMealButton: AppCompatImageView = view.findViewById(R.id.deleteMealButton)

        fun bind(mealAdapterModel: MealAdapterModel) {
            val resources = rootView.context.resources

            mealTextTextView.text = mealAdapterModel.text
            @Suppress("DEPRECATION")
            mealCaloriesTextView.text = String.format(Locale.ENGLISH, "%s%s", Html.fromHtml(resources.getString(R.string.meal_calories_placeholder)), mealAdapterModel.calories)
            @Suppress("DEPRECATION")
            mealWeightTextView.text =
                String.format(Locale.ENGLISH, "%s%sg",
                    Html.fromHtml(resources.getString(R.string.meal_weight_placeholder)), mealAdapterModel.weight)

            if(mealAdapterModel.isLimitExceeded) { rootView.setBackgroundColor(ContextCompat.getColor(rootView.context, R.color.coral_transparent)) }
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

    fun markOnLimitExceeded(items: List<MealAdapterModel>) = with(meals) {
        meals
            .filter { items.containsId(it.id) }
            .map {
                it.isLimitExceeded = true
                notifyItemChanged(meals.indexOf(it))
            }
    }

    override fun onBindViewHolder(holder: MealViewHolder, position: Int) = with(holder) {
        val mealAdapterModel = meals[position]

        bind(mealAdapterModel)

        itemView.setOnClickListener { mealSelectedLiveData.value = mealAdapterModel }

        deleteMealButton.setOnClickListener { mealDeleteClickedLiveData.value = mealAdapterModel.toDeleteParams(position) }
    }
}