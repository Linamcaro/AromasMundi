package com.example.aromasmundi.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.aromasmundi.data.database.entities.FavoritesEntity
import com.example.aromasmundi.data.database.entities.RecipesEntity

@Database(entities = [RecipesEntity::class , FavoritesEntity::class],
    version = 1,
    exportSchema = false)
@TypeConverters(RecipesTypeConverter::class)
abstract class RecipesDataBase: RoomDatabase() {
    abstract fun recipesDao(): RecipesDao

}