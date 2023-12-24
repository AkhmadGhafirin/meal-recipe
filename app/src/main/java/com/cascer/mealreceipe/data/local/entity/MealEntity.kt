package com.cascer.mealreceipe.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "meal")
data class MealEntity(
    @PrimaryKey
    @ColumnInfo("idMeal")
    val idMeal: String,
    @ColumnInfo("strMeal")
    val strMeal: String,
    @ColumnInfo("strMealThumb")
    val strMealThumb: String,
)
