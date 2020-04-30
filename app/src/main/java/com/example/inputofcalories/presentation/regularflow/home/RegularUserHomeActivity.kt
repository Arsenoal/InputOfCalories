package com.example.inputofcalories.presentation.regularflow.home

import android.content.DialogInterface
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.View.VISIBLE
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.inputofcalories.R
import com.example.inputofcalories.entity.presentation.regular.MealFilterParams
import com.example.inputofcalories.presentation.auth.AuthActivity
import com.example.inputofcalories.presentation.regularflow.home.RegularFlowObserversFactory.ObserverKey
import com.example.inputofcalories.presentation.base.BaseActivity
import com.example.inputofcalories.presentation.common.EndlessRecyclerViewScrollListener
import com.example.inputofcalories.presentation.common.ProgressView
import com.example.inputofcalories.presentation.commonextras.ExtraKeys.MEAL_EXTRA
import com.example.inputofcalories.presentation.navigation.ActivityNavigator
import com.example.inputofcalories.presentation.navigation.FragmentNavigator
import com.example.inputofcalories.presentation.regularflow.addmeal.AddMealActivity
import com.example.inputofcalories.presentation.regularflow.home.filter.FilterFragment
import com.example.inputofcalories.presentation.regularflow.home.viewmodel.*
import com.example.inputofcalories.presentation.regularflow.model.MealSerializable
import com.example.inputofcalories.presentation.regularflow.viewmeal.ViewMealActivity
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.android.synthetic.main.activity_regular_user_home.*
import kotlinx.android.synthetic.main.activity_regular_user_home.editMealButton
import kotlinx.android.synthetic.main.progress_layout.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.android.viewmodel.ext.android.viewModel

class RegularUserHomeActivity : BaseActivity(), ProgressView {

    private val mealsViewModel: MealsViewModel by viewModel()

    private val dailyCaloriesViewModel: DailyCaloriesViewModel by viewModel()

    private val filterFragment = FilterFragment.newInstance()

    private lateinit var observerFactory: RegularFlowObserversFactory

    private lateinit var endlessRecyclerViewScrollListener: EndlessRecyclerViewScrollListener

    var filterFlow: Flow<List<MealFilterParams>>? = null
    set(value) {
        field = value
        lifecycleScope.launch {
            field?.let { flow ->
                flow.collect { listParams ->
                    mealsViewModel
                        .getMealsFiltered(listParams)
                        .observe(this@RegularUserHomeActivity, observerFactory.get(ObserverKey.GetMealsFilteredObserver))
                }
            }
        }
    }

    private val mealsAdapter = MealsRecyclerAdapter()

    private var isFilterOpened = false

    private var itemsTodayFound = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_regular_user_home)

        setupToolbar()

        initObserverFactory()

        setupMealsRecyclerView()

        setupClickListeners()

        checkDailyCaloriesLimit()
    }

    private fun setupToolbar() {
        setSupportActionBar(toolbar)
    }

    override fun onResume() {
        super.onResume()

        itemsTodayFound = true
        endlessRecyclerViewScrollListener.resetState()
        loadMeals()
    }

    private fun initObserverFactory() {
        observerFactory = RegularFlowObserversFactory(this, mealsAdapter)
    }

    private fun loadMeals() {
        showProgress()
        mealsViewModel.loadMeals().observe(this, observerFactory.get(ObserverKey.GetMealsObserver))
    }

    private fun checkDailyCaloriesLimit() {
        dailyCaloriesViewModel
            .checkDailyLimit()
            .observe(this, observerFactory.get(ObserverKey.CheckDailyLimitObserver))
    }

    fun loadMore() {
        itemsTodayFound = false
        mealsViewModel.loadMoreMeals(1).observe(this@RegularUserHomeActivity, observerFactory.get(ObserverKey.GetMoreMealsObserver))
    }

    fun showReloadMealsOption() {

    }

    private fun setupMealsRecyclerView() {
        val layoutManager =  LinearLayoutManager(this)
        mealsRecyclerView.layoutManager = layoutManager
        mealsRecyclerView.adapter = mealsAdapter

        mealsAdapter.mealSelectedLiveData.observe(this, Observer {
            val mealSerializable = with(it) {
                MealSerializable(id, text, calories, weight, year, month, dayOfMonth, timeParams)
            }

            ActivityNavigator.navigate(this, ViewMealActivity::class.java, MEAL_EXTRA, mealSerializable)
        })

        mealsAdapter.mealDeleteClickedLiveData.observe(this, Observer { deleteParams ->
             val dialog = MaterialAlertDialogBuilder(this)
                .setTitle(getString(R.string.do_you_want_to_delete_meal))
                .setPositiveButton(R.string.delete) { dialog, _ ->
                    dialog.dismiss()
                    showProgress()

                    mealsViewModel.deleteMeal(deleteParams).observe(this, observerFactory.get(ObserverKey.DeleteMealObserver))
                }
                .setNegativeButton(R.string.cancel) { dialog, _ ->
                    dialog.dismiss()
                }
                .create()

            dialog.show()

            dialog.getButton(DialogInterface.BUTTON_POSITIVE).setTextColor(ContextCompat.getColor(this, R.color.red))
            dialog.getButton(DialogInterface.BUTTON_NEGATIVE).setTextColor(ContextCompat.getColor(this, R.color.red))
        })

        endlessRecyclerViewScrollListener = object: EndlessRecyclerViewScrollListener(layoutManager) {
            override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView?) {
                val mPage = if(itemsTodayFound) page else page + 1

                mealsViewModel.loadMoreMeals(mPage).observe(this@RegularUserHomeActivity, observerFactory.get(ObserverKey.GetMoreMealsObserver))
            }
        }

        mealsRecyclerView.addOnScrollListener(endlessRecyclerViewScrollListener)
    }

    private fun setupClickListeners() {
        editMealButton.setOnClickListener {
            ActivityNavigator.navigate(this, AddMealActivity::class.java)
        }
    }

    fun deleteMeal(position: Int) {
        mealsAdapter.deleteItem(position)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_regular_user_home, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.actionFilter -> {
                isFilterOpened = if(!isFilterOpened) {
                    FragmentNavigator.openOrReplace(this, filterFragment, filterFrame.id, null)
                    true
                } else {
                    FragmentNavigator.remove(this, filterFragment)
                    false
                }
            }
            R.id.dailyCalories -> {
                val dialog = SetDailyCaloriesDialog(this)

                dialog.dailyCaloriesLiveData.observe(this, Observer { dailyCalories ->
                    showProgress()

                    dailyCaloriesViewModel.saveDailyCaloriesLimit(dailyCalories).observe(this, observerFactory.get(ObserverKey.UpdateDailyCaloriesObserver))
                })
                dialog.show()
            }
            R.id.logOut -> {
                ActivityNavigator.navigateAndClearStack(this, AuthActivity::class.java)
            }
        }

        return true
    }

    override fun showProgress() { progressContainer.visibility = VISIBLE }

    override fun hideProgress() { progressContainer.visibility = View.GONE }
}
