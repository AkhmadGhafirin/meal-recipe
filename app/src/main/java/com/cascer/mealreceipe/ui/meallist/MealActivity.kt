package com.cascer.mealreceipe.ui.meallist

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.cascer.mealreceipe.R
import com.cascer.mealreceipe.data.Resource
import com.cascer.mealreceipe.databinding.ActivityMealBinding
import com.cascer.mealreceipe.domain.model.Meal
import com.cascer.mealreceipe.ui.bookmark.BookmarkActivity
import com.cascer.mealreceipe.ui.filter.FilterActivity
import com.cascer.mealreceipe.ui.mealdetail.DetailActivity
import com.cascer.mealreceipe.ui.mealdetail.DetailActivity.Companion.EXTRA_ID
import com.cascer.mealreceipe.ui.search.SearchActivity
import com.cascer.mealreceipe.utils.gone
import com.cascer.mealreceipe.utils.visible
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MealActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMealBinding
    private val viewModel: MealViewModel by viewModels()
    private val mealsAdapter by lazy { MealAdapter { toDetail(it) } }
    private var category = ""
    private var area = "American"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMealBinding.inflate(layoutInflater)
        setContentView(binding.root)
        category = intent.getStringExtra(EXTRA_CATEGORY).orEmpty()
        setupViews()
        requestData()
    }

    private fun setupViews() {
        with(binding) {
            toolbar.setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.search -> {
                        startActivity(Intent(this@MealActivity, SearchActivity::class.java))
                        true
                    }

                    R.id.bookmark -> {
                        startActivity(Intent(this@MealActivity, BookmarkActivity::class.java))
                        true
                    }

                    R.id.filter -> {
                        val intent = Intent(this@MealActivity, FilterActivity::class.java)
                        intent.putExtra(EXTRA_CATEGORY, category)
                        intent.putExtra(EXTRA_AREA, area)
                        launchFilterActivity.launch(intent)
                        true
                    }

                    else -> false
                }
            }
            rvMeals.apply {
                layoutManager =
                    GridLayoutManager(this@MealActivity, 2, GridLayoutManager.VERTICAL, false)
                adapter = mealsAdapter
            }
        }
    }

    private fun requestData() {
        with(viewModel) {
            meals(category, area).observe(this@MealActivity) {
                if (it != null) {
                    when (it) {
                        is Resource.Success -> {
                            binding.progressbar.gone()
                            it.data?.let { list -> mealsAdapter.sendData(list) }
                        }

                        is Resource.Loading -> {
                            binding.progressbar.visible()
                        }

                        is Resource.Error -> {
                            binding.progressbar.gone()
                            Toast.makeText(this@MealActivity, it.message, Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }
    }

    private fun toDetail(meal: Meal) {
        startActivity(Intent(this@MealActivity, DetailActivity::class.java).apply {
            putExtra(EXTRA_ID, meal.idMeal)
        })
    }

    private val launchFilterActivity = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            val data = result.data
            category = data?.getStringExtra(EXTRA_CATEGORY).orEmpty()
            area = data?.getStringExtra(EXTRA_AREA).orEmpty()
            requestData()
        }
    }

    companion object {
        const val EXTRA_CATEGORY = "category"
        const val EXTRA_AREA = "area"
    }
}