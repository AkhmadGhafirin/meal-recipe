package com.cascer.mealreceipe.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.cascer.mealreceipe.domain.usecase.MealUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val mealUseCase: MealUseCase
) : ViewModel() {
    fun search(query: String) = mealUseCase.searchMeals(query).asLiveData()
}