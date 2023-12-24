package com.cascer.mealreceipe.domain.usecase

import com.cascer.mealreceipe.data.Resource
import com.cascer.mealreceipe.domain.model.Category
import com.cascer.mealreceipe.domain.model.Meal
import com.cascer.mealreceipe.domain.repository.MealRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MealUseCaseImpl @Inject constructor(
    private val mealRepository: MealRepository
) : MealUseCase {
    override fun filterMeals(category: String?, area: String?): Flow<Resource<List<Meal>>> {
        return mealRepository.filterMeals(category, area)
    }

    override fun searchMeals(query: String): Flow<Resource<List<Meal>>> {
        return mealRepository.searchMeals(query)
    }

    override fun getCategories(): Flow<Resource<List<Category>>> {
        return mealRepository.getCategories()
    }

    override fun detailMeal(id: String): Flow<Resource<Meal>> {
        return mealRepository.detailMeal(id)
    }

    override fun getStatusBookmark(id: String): Flow<Meal> {
        return mealRepository.getStatusBookmark(id)
    }

    override fun bookmarkMeal(meal: Meal, newState: Boolean) {
        return mealRepository.bookmarkMeal(meal, newState)
    }

    override fun getBookmarks(): Flow<List<Meal>> {
        return mealRepository.getBookmarks()
    }
}