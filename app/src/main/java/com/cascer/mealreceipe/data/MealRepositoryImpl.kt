package com.cascer.mealreceipe.data

import com.cascer.mealreceipe.data.local.LocalDataSource
import com.cascer.mealreceipe.data.remote.RemoteDataSource
import com.cascer.mealreceipe.data.remote.network.ApiResponse
import com.cascer.mealreceipe.domain.model.Category
import com.cascer.mealreceipe.domain.model.Meal
import com.cascer.mealreceipe.domain.repository.MealRepository
import com.cascer.mealreceipe.utils.AppExecutors
import com.cascer.mealreceipe.utils.DataMapper.emptyMeal
import com.cascer.mealreceipe.utils.DataMapper.toDomain
import com.cascer.mealreceipe.utils.DataMapper.toEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class MealRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val appExecutors: AppExecutors
) : MealRepository {
    override fun filterMeals(category: String?, area: String?): Flow<Resource<List<Meal>>> = flow {
        emit(Resource.Loading())
        when (val apiResponse = remoteDataSource.filterMeals(category, area).first()) {
            is ApiResponse.Success -> {
                emit(Resource.Success(apiResponse.data.map { it.toDomain() }))
            }

            is ApiResponse.Empty -> {
                emit(Resource.Success(listOf()))
            }

            is ApiResponse.Error -> {
                emit(Resource.Error(apiResponse.errorMessage))
            }
        }
    }

    override fun searchMeals(query: String): Flow<Resource<List<Meal>>> = flow {
        emit(Resource.Loading())
        when (val apiResponse = remoteDataSource.searchMeals(query).first()) {
            is ApiResponse.Success -> {
                emit(Resource.Success(apiResponse.data.map { it.toDomain() }))
            }

            is ApiResponse.Empty -> {
                emit(Resource.Success(listOf()))
            }

            is ApiResponse.Error -> {
                emit(Resource.Error(apiResponse.errorMessage))
            }
        }
    }

    override fun getCategories(): Flow<Resource<List<Category>>> = flow {
        emit(Resource.Loading())
        when (val apiResponse = remoteDataSource.getCategories().first()) {
            is ApiResponse.Success -> {
                emit(Resource.Success(apiResponse.data.map { it.toDomain() }))
            }

            is ApiResponse.Empty -> {
                emit(Resource.Success(listOf()))
            }

            is ApiResponse.Error -> {
                emit(Resource.Error(apiResponse.errorMessage))
            }
        }
    }

    override fun detailMeal(id: String): Flow<Resource<Meal>> = flow {
        emit(Resource.Loading())
        when (val apiResponse = remoteDataSource.detailMeal(id).first()) {
            is ApiResponse.Success -> {
                emit(Resource.Success(apiResponse.data.toDomain()))
            }

            is ApiResponse.Empty -> {
                emit(Resource.Success(emptyMeal()))
            }

            is ApiResponse.Error -> {
                emit(Resource.Error(apiResponse.errorMessage))
            }
        }
    }

    override fun getStatusBookmark(id: String): Flow<Meal> {
        return localDataSource.getStatusBookmark(id).map { it?.toDomain() ?: emptyMeal() }
    }

    override fun bookmarkMeal(meal: Meal, newState: Boolean) {
        appExecutors.diskIO().execute {
            if (newState) {
                localDataSource.addBookmark(meal.toEntity())
            } else {
                localDataSource.deleteBookmark(meal.idMeal)
            }
        }
    }

    override fun getBookmarks(): Flow<List<Meal>> {
        return localDataSource.getBookmarks().map { it.map { movie -> movie.toDomain() } }
    }
}