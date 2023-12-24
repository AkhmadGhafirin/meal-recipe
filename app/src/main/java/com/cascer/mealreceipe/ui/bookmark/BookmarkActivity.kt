package com.cascer.mealreceipe.ui.bookmark

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.cascer.mealreceipe.databinding.ActivityBookmarkBinding
import com.cascer.mealreceipe.domain.model.Meal
import com.cascer.mealreceipe.ui.mealdetail.DetailActivity
import com.cascer.mealreceipe.ui.meallist.MealAdapter
import com.cascer.mealreceipe.utils.gone
import com.cascer.mealreceipe.utils.visible
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BookmarkActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBookmarkBinding
    private val viewModel: BookmarkViewModel by viewModels()
    private val mealsAdapter by lazy { MealAdapter { toDetail(it) } }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBookmarkBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupViews()
        requestData()
    }

    private fun setupViews() {
        with(binding) {
            progressbar.visible()
            rvBookmarks.apply {
                layoutManager =
                    GridLayoutManager(this@BookmarkActivity, 2, GridLayoutManager.VERTICAL, false)
                adapter = mealsAdapter
            }
        }
    }

    private fun requestData() {
        with(viewModel) {
            bookmarks.observe(this@BookmarkActivity) {
                binding.progressbar.gone()
                mealsAdapter.sendData(it)
            }
        }
    }

    private fun toDetail(meal: Meal) {
        startActivity(Intent(this@BookmarkActivity, DetailActivity::class.java).apply {
            putExtra(DetailActivity.EXTRA_ID, meal.idMeal)
        })
    }
}