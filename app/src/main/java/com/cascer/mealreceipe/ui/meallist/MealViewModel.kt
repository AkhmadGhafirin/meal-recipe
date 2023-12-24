package com.cascer.mealreceipe.ui.meallist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.cascer.mealreceipe.domain.usecase.MealUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MealViewModel @Inject constructor(
    private val mealUseCase: MealUseCase
) : ViewModel() {
    fun meals(category: String, area: String) = mealUseCase.filterMeals(category, area).asLiveData()
}