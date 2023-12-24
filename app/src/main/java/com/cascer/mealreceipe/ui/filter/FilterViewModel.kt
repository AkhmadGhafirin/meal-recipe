package com.cascer.mealreceipe.ui.filter

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.cascer.mealreceipe.domain.usecase.MealUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FilterViewModel @Inject constructor(
    private val mealUseCase: MealUseCase
) : ViewModel() {
    val categories = mealUseCase.getCategories().asLiveData()
}