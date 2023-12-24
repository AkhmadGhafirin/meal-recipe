package com.cascer.mealreceipe.di

import com.cascer.mealreceipe.data.MealRepositoryImpl
import com.cascer.mealreceipe.domain.repository.MealRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module(includes = [DatabaseModule::class, NetworkModule::class])
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract fun provideMealRepository(mealRepository: MealRepositoryImpl): MealRepository
}