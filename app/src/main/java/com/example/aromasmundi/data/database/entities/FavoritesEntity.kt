package com.example.aromasmundi.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.aromasmundi.models.Result
import com.example.aromasmundi.util.Constants.Companion.FAVORITE_RECIPES_TABLE

@Entity(tableName = FAVORITE_RECIPES_TABLE)
class FavoritesEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    var result: Result
)