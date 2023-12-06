package com.example.aromasmundi.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.aromasmundi.models.RecipeResponse
import com.example.aromasmundi.util.Constants.Companion.RECIPES_TABLE

@Entity(tableName = RECIPES_TABLE)
data class RecipesEntity(
    var foodRecipe: RecipeResponse
) {

    @PrimaryKey(autoGenerate = false)
    var id: Int = 0

}