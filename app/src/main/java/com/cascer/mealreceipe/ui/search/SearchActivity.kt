package com.cascer.mealreceipe.ui.search

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.cascer.mealreceipe.data.Resource
import com.cascer.mealreceipe.databinding.ActivitySearchBinding
import com.cascer.mealreceipe.domain.model.Meal
import com.cascer.mealreceipe.ui.mealdetail.DetailActivity
import com.cascer.mealreceipe.ui.mealdetail.DetailActivity.Companion.EXTRA_ID
import com.cascer.mealreceipe.ui.meallist.MealAdapter
import com.cascer.mealreceipe.utils.gone
import com.cascer.mealreceipe.utils.visible
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySearchBinding
    private val viewModel: SearchViewModel by viewModels()
    private val mealsAdapter by lazy { MealAdapter { toDetail(it) } }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupViews()
    }

    private fun setupViews() {
        with(binding) {
            toolbar.setNavigationOnClickListener { finish() }
            searchView.setupWithSearchBar(searchBar)
            searchView.editText.setOnEditorActionListener { _, _, _ ->
                searchBar.setText(searchView.text)
                searchView.hide()
                requestData()
                false
            }

            rvMeals.apply {
                layoutManager =
                    GridLayoutManager(this@SearchActivity, 2, GridLayoutManager.VERTICAL, false)
                adapter = mealsAdapter
            }
        }
    }

    private fun requestData() {
        with(viewModel) {
            search(binding.searchView.text.toString()).observe(this@SearchActivity) {
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
                            Toast.makeText(this@SearchActivity, it.message, Toast.LENGTH_SHORT)
                                .show()
                        }
                    }
                }
            }
        }
    }

    private fun toDetail(meal: Meal) {
        startActivity(Intent(this@SearchActivity, DetailActivity::class.java).apply {
            putExtra(EXTRA_ID, meal.idMeal)
        })
    }
}