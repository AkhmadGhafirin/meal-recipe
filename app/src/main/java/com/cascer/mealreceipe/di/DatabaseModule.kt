package com.cascer.mealreceipe.di

import android.content.Context
import com.cascer.mealreceipe.data.local.room.MealDao
import com.cascer.mealreceipe.data.local.room.MealDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Singleton
    @Provides
    fun provideMealDatabase(@ApplicationContext context: Context): MealDatabase {
        return MealDatabase.getDatabase(context)
    }

    @Provides
    fun provideMealDao(database: MealDatabase): MealDao {
        return database.dao()
    }
}