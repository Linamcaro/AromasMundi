package com.example.aromasmundi.data.database

import androidx.room.TypeConverter
import com.example.aromasmundi.models.RecipeResponse
import com.example.aromasmundi.models.Result
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class RecipesTypeConverter {

    var gson = Gson()

    //Convert a RecipeResponse into a string
    @TypeConverter
    fun foodRecipeToString(recipeData: RecipeResponse): String {

        return gson.toJson(recipeData)
    }

    //Retrieve the string data and convert it back into a RecipeResponse
    @TypeConverter
    fun stringToFoodRecipe(data: String): RecipeResponse {
        val listType = object : TypeToken <RecipeResponse>() {}.type
        return gson.fromJson(data,listType)
    }

    @TypeConverter
    fun resultToString(result: Result): String {
        return gson.toJson(Result)
    }

    @TypeConverter
    fun stringToResult(data: String): Result {
        val listType = object : TypeToken <Result>() {}.type
        return gson.fromJson(data,listType)
    }
}