package com.cascer.mealreceipe.data.local.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.cascer.mealreceipe.data.local.entity.MealEntity

@Database(entities = [MealEntity::class], version = 1, exportSchema = false)
abstract class MealDatabase : RoomDatabase() {
    abstract fun dao(): MealDao

    companion object {
        @Volatile
        private var INSTANCE: MealDatabase? = null

        @JvmStatic
        fun getDatabase(context: Context): MealDatabase {
            if (INSTANCE == null) {
                synchronized(MealDatabase::class.java) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext, MealDatabase::class.java, "meal_database"
                    ).fallbackToDestructiveMigration().build()
                }
            }
            return INSTANCE as MealDatabase
        }
    }
}