package com.example.aromasmundi.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.aromasmundi.util.Constants.Companion.RECIPES_TABLE
import kotlinx.coroutines.flow.Flow

@Dao
interface RecipesDao {

    //insert new data to the recipes entity and replace the old data with the new one
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecipes(recipesEntity: RecipesEntity)

    //get all the recipes
    @Query("SELECT * FROM $RECIPES_TABLE ORDER BY id ASC")
    fun getAllRecipes(): Flow<List<RecipesEntity>>

}