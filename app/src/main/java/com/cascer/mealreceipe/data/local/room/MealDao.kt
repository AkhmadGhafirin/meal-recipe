package com.cascer.mealreceipe.data.local.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.cascer.mealreceipe.data.local.entity.MealEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MealDao {
    @Query("SELECT * FROM meal")
    fun getBookmarks(): Flow<List<MealEntity>>

    @Query("SELECT * FROM meal WHERE idMeal = :id")
    fun getStatusBookmark(id: String): Flow<MealEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addBookmark(meal: MealEntity)

    @Query("DELETE FROM meal WHERE idMeal = :id")
    fun deleteBookmark(id: String)
}