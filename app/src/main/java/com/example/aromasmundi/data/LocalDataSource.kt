package com.example.aromasmundi.data

import androidx.room.Dao
import com.example.aromasmundi.data.database.RecipesDao
import com.example.aromasmundi.data.database.RecipesEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalDataSource @Inject constructor(
    private val recipesDao: RecipesDao
){

    suspend fun insertRecipes(recipesEntity: RecipesEntity) {
        recipesDao.insertRecipes(recipesEntity)
    }

    fun getAllRecipes(): Flow<List<RecipesEntity>> {
        return recipesDao.getAllRecipes()
    }

}