package com.example.aromasmundi.data

import com.example.aromasmundi.data.network.RecipesAPI
import com.example.aromasmundi.models.RecipeResponse
import retrofit2.Response
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val recipesAPI: RecipesAPI
) {
    suspend fun getRecipes(queries: Map<String, String>): Response<RecipeResponse> {
        return recipesAPI.getRecipes(queries)
    }

}