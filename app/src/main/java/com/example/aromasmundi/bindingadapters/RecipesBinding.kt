package com.example.aromasmundi.bindingadapters

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import com.example.aromasmundi.data.database.entities.RecipesEntity
import com.example.aromasmundi.models.RecipeResponse
import com.example.aromasmundi.util.NetworkResult

class RecipesBinding {
    companion object{
        @BindingAdapter("readApiResponse", "readDatabase", requireAll = true)
        @JvmStatic
        fun readDataError(view: View,
                                 apiResponse: NetworkResult<RecipeResponse>?,
                                 database: List<RecipesEntity>?){

            when (view){
                is ImageView ->{
                    view.isVisible = apiResponse is NetworkResult.Error && database.isNullOrEmpty()
                }
                is TextView ->{
                    view.isVisible = apiResponse is NetworkResult.Error && database.isNullOrEmpty()
                    view.text = apiResponse?.message.toString()
                }
            }

        }
    }
}