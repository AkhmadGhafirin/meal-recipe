package com.cascer.mealreceipe.data.remote.response

import com.google.gson.annotations.SerializedName

data class CategoryResponse(
    @SerializedName("idCategory")
    val idCategory: String?,
    @SerializedName("strCategory")
    val strCategory: String?,
    @SerializedName("strCategoryThumb")
    val strCategoryThumb: String?,
    @SerializedName("strCategoryDescription")
    val strCategoryDescription: String?,
)
