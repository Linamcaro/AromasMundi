package com.example.aromasmundi.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.aromasmundi.data.database.entities.FavoritesEntity
import com.example.aromasmundi.data.database.entities.RecipesEntity
import com.example.aromasmundi.util.Constants.Companion.RECIPES_TABLE
import kotlinx.coroutines.flow.Flow

@Dao
interface RecipesDao {

    //* RECIPES QUERIES *//
    //insert new data to the recipes entity and replace the old data with the new one
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecipes(recipesEntity: RecipesEntity)

    //get all the recipes
    @Query("SELECT * FROM $RECIPES_TABLE ORDER BY id ASC")
    fun getAllRecipes(): Flow<List<RecipesEntity>>


    //* FAVORITES QUERIES *//
    //insert a new favorite recipe
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavoriteRecipe(favoritesEntity: FavoritesEntity)

    //get all the favorite recipes
    @Query("SELECT * FROM favorite_recipes_table ORDER BY id ASC")
    fun getFavoriteRecipes(): Flow<List<FavoritesEntity>>

    //Delete a specific recipe from favorites
    @Delete
    suspend fun deleteFavoriteRecipe(favoritesEntity: FavoritesEntity)

    //Delete all the recipes from favorites
    @Query("DELETE FROM favorite_recipes_table")
    suspend fun deleteAllFavoriteRecipes()


}