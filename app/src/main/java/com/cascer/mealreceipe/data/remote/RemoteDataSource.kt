package com.cascer.mealreceipe.data.remote

import com.cascer.mealreceipe.data.remote.network.ApiResponse
import com.cascer.mealreceipe.data.remote.network.ApiService
import com.cascer.mealreceipe.data.remote.response.CategoryResponse
import com.cascer.mealreceipe.data.remote.response.MealResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RemoteDataSource @Inject constructor(private val apiService: ApiService) {

    suspend fun filterMeals(
        category: String? = "", area: String? = ""
    ): Flow<ApiResponse<List<MealResponse>>> {
        return flow {
            try {
                val options = hashMapOf<String, String>()
                if (category?.isNotEmpty() == true) options["c"] = category
                if (area?.isNotEmpty() == true) options["a"] = area
                val response = apiService.filterMeals(options)
                val data = response.meals
                if (data.isNotEmpty()) emit(ApiResponse.Success(data))
                else emit(ApiResponse.Empty)
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun searchMeals(query: String): Flow<ApiResponse<List<MealResponse>>> {
        return flow {
            try {
                val response = apiService.searchMeals(search = query)
                val data = response.meals
                if (data.isNotEmpty()) emit(ApiResponse.Success(data))
                else emit(ApiResponse.Empty)
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getCategories(): Flow<ApiResponse<List<CategoryResponse>>> {
        return flow {
            try {
                val response = apiService.getCategories()
                val data = response.categories
                if (data.isNotEmpty()) emit(ApiResponse.Success(data))
                else emit(ApiResponse.Empty)
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun detailMeal(id: String): Flow<ApiResponse<MealResponse>> {
        return flow {
            try {
                val response = apiService.detailMeal(id)
                val data = response.meals
                if (data.isNotEmpty()) emit(ApiResponse.Success(data.first()))
                else emit(ApiResponse.Empty)
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
            }
        }.flowOn(Dispatchers.IO)
    }
}