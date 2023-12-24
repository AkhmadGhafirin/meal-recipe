package com.cascer.mealreceipe.domain.usecase

import com.cascer.mealreceipe.data.Resource
import com.cascer.mealreceipe.domain.model.Category
import com.cascer.mealreceipe.domain.model.Meal
import kotlinx.coroutines.flow.Flow

interface MealUseCase {
    fun filterMeals(category: String? = "", area: String? = ""): Flow<Resource<List<Meal>>>
    fun searchMeals(query: String): Flow<Resource<List<Meal>>>
    fun getCategories(): Flow<Resource<List<Category>>>
    fun detailMeal(id: String): Flow<Resource<Meal>>
    fun getStatusBookmark(id: String): Flow<Meal>
    fun bookmarkMeal(meal: Meal, newState: Boolean)
    fun getBookmarks(): Flow<List<Meal>>
}