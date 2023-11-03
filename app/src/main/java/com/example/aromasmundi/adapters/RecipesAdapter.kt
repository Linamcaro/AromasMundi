package com.example.aromasmundi.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.aromasmundi.databinding.RecipesRowLayoutBinding
import com.example.aromasmundi.models.RecipeResponse
import com.example.aromasmundi.models.Result
import com.example.aromasmundi.util.RecipesDiffUtil

class RecipesAdapter: RecyclerView.Adapter<RecipesAdapter.RecipeViewHolder>() {

    private var recipesList = emptyList<Result>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {

        return RecipeViewHolder.from(parent)
    }

    //Update the recycler view each time we result new data from the API
    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        val currentRecipes = recipesList[position]
        holder.bind(currentRecipes)
    }

    override fun getItemCount(): Int {
        return recipesList.size
    }

    class RecipeViewHolder(private val binding: RecipesRowLayoutBinding): RecyclerView.ViewHolder(binding.root) {

        fun bind(result: Result) {
            binding.result = result
            binding.executePendingBindings()
        }

        //inflating the view layout
        companion object{
            fun from(parent: ViewGroup): RecipeViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = RecipesRowLayoutBinding.inflate(layoutInflater, parent, false)
                return RecipeViewHolder(binding)
            }
        }
    }

    //Setting the data
    fun setData(newData : RecipeResponse) {
            val recipesDiffUtil = RecipesDiffUtil(recipesList, newData.results)
            //calculate the difference between the old and new data
            val diffUtilResult = DiffUtil.calculateDiff(recipesDiffUtil)
            recipesList = newData.results
            diffUtilResult.dispatchUpdatesTo(this)

    }

}