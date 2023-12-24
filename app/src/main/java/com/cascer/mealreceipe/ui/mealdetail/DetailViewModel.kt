package com.cascer.mealreceipe.ui.mealdetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.cascer.mealreceipe.domain.model.Meal
import com.cascer.mealreceipe.domain.usecase.MealUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val mealUseCase: MealUseCase
) : ViewModel() {
    fun getDetail(id: String) = mealUseCase.detailMeal(id).asLiveData()
    fun statusBookmark(id: String) = mealUseCase.getStatusBookmark(id).asLiveData()
    fun addBookmark(meal: Meal, newState: Boolean) =
        mealUseCase.bookmarkMeal(meal, newState)
}