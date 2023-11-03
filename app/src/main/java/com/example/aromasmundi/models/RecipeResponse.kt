package com.example.aromasmundi.models


import com.google.gson.annotations.SerializedName

data class RecipeResponse(
    @SerializedName("results")
    val results: List<Result>,
)