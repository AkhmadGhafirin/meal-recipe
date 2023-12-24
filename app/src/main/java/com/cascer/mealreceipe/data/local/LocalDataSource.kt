package com.cascer.mealreceipe.data.local

import com.cascer.mealreceipe.data.local.entity.MealEntity
import com.cascer.mealreceipe.data.local.room.MealDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocalDataSource @Inject constructor(
    private val mealDao: MealDao
) {
    fun getBookmarks(): Flow<List<MealEntity>> = mealDao.getBookmarks()

    fun getStatusBookmark(id: String): Flow<MealEntity?> = mealDao.getStatusBookmark(id)

    fun addBookmark(meal: MealEntity) = mealDao.addBookmark(meal)

    fun deleteBookmark(id: String) = mealDao.deleteBookmark(id)
}