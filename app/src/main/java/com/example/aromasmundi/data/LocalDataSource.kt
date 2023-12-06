package com.example.aromasmundi.data

import com.example.aromasmundi.data.database.RecipesDao
import com.example.aromasmundi.data.database.entities.FavoritesEntity
import com.example.aromasmundi.data.database.entities.RecipesEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalDataSource @Inject constructor(
    private val recipesDao: RecipesDao
){

    //* RECIPES *//
    suspend fun insertRecipes(recipesEntity: RecipesEntity) {
        recipesDao.insertRecipes(recipesEntity)
    }

    fun getAllRecipes(): Flow<List<RecipesEntity>> {
        return recipesDao.getAllRecipes()
    }

    //* FAVORITES *//
    fun getFavoriteRecipes(): Flow<List<FavoritesEntity>> {
        return recipesDao.getFavoriteRecipes()
    }

    suspend fun insertFavoriteRecipe(favoritesEntity: FavoritesEntity) {
        recipesDao.insertFavoriteRecipe(favoritesEntity)
    }

    suspend fun deleteFavoriteRecipe(favoritesEntity: FavoritesEntity) {
        recipesDao.deleteFavoriteRecipe(favoritesEntity)
    }

    suspend fun deleteAllFavoriteRecipes() {
        recipesDao.deleteAllFavoriteRecipes()
    }

}