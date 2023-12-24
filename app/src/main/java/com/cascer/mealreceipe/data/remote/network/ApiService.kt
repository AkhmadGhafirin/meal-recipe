package com.cascer.mealreceipe.data.remote.network

import com.cascer.mealreceipe.data.remote.response.ListCategoryResponse
import com.cascer.mealreceipe.data.remote.response.ListMealResponse
import com.cascer.mealreceipe.data.remote.response.MealResponse
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.QueryMap

interface ApiService {
    @GET("filter.php")
    suspend fun filterMeals(
        @QueryMap options: Map<String, String>
    ): ListMealResponse

    @GET("search.php")
    suspend fun searchMeals(
        @Query("s") search: String
    ): ListMealResponse

    @GET("categories.php")
    suspend fun getCategories(): ListCategoryResponse

    @GET("lookup.php")
    suspend fun detailMeal(
        @Query("i") id: String
    ): ListMealResponse
}