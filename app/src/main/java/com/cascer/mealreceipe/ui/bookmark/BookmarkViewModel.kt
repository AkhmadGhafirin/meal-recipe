package com.cascer.mealreceipe.ui.bookmark

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.cascer.mealreceipe.domain.usecase.MealUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class BookmarkViewModel @Inject constructor(
    private val mealUseCase: MealUseCase
) : ViewModel() {
    val bookmarks = mealUseCase.getBookmarks().asLiveData()
}