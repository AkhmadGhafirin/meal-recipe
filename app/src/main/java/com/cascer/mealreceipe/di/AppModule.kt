package com.cascer.mealreceipe.di

import com.cascer.mealreceipe.domain.usecase.MealUseCase
import com.cascer.mealreceipe.domain.usecase.MealUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AppModule {
    @Binds
    @Singleton
    abstract fun provideMealUseCase(mealUseCase: MealUseCaseImpl): MealUseCase
}