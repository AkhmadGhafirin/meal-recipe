package com.cascer.mealreceipe.ui.filter

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.cascer.mealreceipe.data.Resource
import com.cascer.mealreceipe.databinding.ActivityFilterBinding
import com.cascer.mealreceipe.domain.model.Category
import com.cascer.mealreceipe.ui.meallist.MealActivity.Companion.EXTRA_AREA
import com.cascer.mealreceipe.ui.meallist.MealActivity.Companion.EXTRA_CATEGORY
import com.cascer.mealreceipe.utils.gone
import com.cascer.mealreceipe.utils.visible
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FilterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFilterBinding
    private val viewModel: FilterViewModel by viewModels()
    private val categoryAdapter by lazy { CategoryAdapter { selectCategory(it) } }
    private var category = ""
    private var area = "American"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFilterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupViews()
        requestData()
    }

    private fun setupViews() {
        with(binding) {
            toolbar.apply {
                setNavigationOnClickListener { finish() }
            }
            rvCategory.apply {
                layoutManager =
                    GridLayoutManager(this@FilterActivity, 2, GridLayoutManager.VERTICAL, false)
                adapter = categoryAdapter
            }
            category = intent.getStringExtra(EXTRA_CATEGORY).orEmpty()
            area = intent.getStringExtra(EXTRA_AREA).orEmpty()

            if (area == "American") rbAmerican.isChecked = true
            else rbCanadian.isChecked = true

            rbAmerican.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) area = "American"
            }
            rbCanadian.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) area = "Canadian"
            }
            btnFilter.setOnClickListener {
                val intent = Intent().apply {
                    putExtra(EXTRA_CATEGORY, category)
                    putExtra(EXTRA_AREA, area)
                }
                setResult(RESULT_OK, intent)
                finish()
            }
        }
    }

    private fun requestData() {
        with(viewModel) {
            categories.observe(this@FilterActivity) {
                if (it != null) {
                    when (it) {
                        is Resource.Success -> {
                            binding.progressbar.gone()
                            it.data?.let { list -> categoryAdapter.sendData(list) }
                            if (category.isNotEmpty()) categoryAdapter.setSelected(category)
                        }

                        is Resource.Loading -> {
                            binding.progressbar.visible()
                        }

                        is Resource.Error -> {
                            binding.progressbar.gone()
                            Toast.makeText(this@FilterActivity, it.message, Toast.LENGTH_SHORT)
                                .show()
                        }
                    }
                }
            }
        }
    }

    private fun selectCategory(item: Category) {
        category = item.strCategory
    }
}