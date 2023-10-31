package com.example.aromasmundi.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.aromasmundi.R
import com.example.aromasmundi.databinding.IngredientsRowLayoutBinding
import com.example.aromasmundi.databinding.RecipesRowLayoutBinding
import com.example.aromasmundi.models.ExtendedIngredient
import com.example.aromasmundi.ui.fragments.ingredients.IngredientsDetailFragment
import com.example.aromasmundi.util.Constants.Companion.BASE_IMAGE_URL
import com.example.aromasmundi.util.RecipesDiffUtil
import java.util.*

class IngredientsAdapter: RecyclerView.Adapter<IngredientsAdapter.IngredientsViewHolder>() {

    private var ingredientsList = emptyList<ExtendedIngredient>()

    class IngredientsViewHolder( val binding: IngredientsRowLayoutBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IngredientsViewHolder {
        return IngredientsViewHolder(IngredientsRowLayoutBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false)
        )
    }

    override fun onBindViewHolder(holder: IngredientsViewHolder, position: Int) {
        holder.binding.ingredientImageView.load(BASE_IMAGE_URL + ingredientsList[position].image)
        {
            crossfade(600)
            error(R.drawable.ic_error_placeholder)
        }

        holder.binding.ingredientName.text = ingredientsList[position].name
        holder.binding.ingredientAmount.text = ingredientsList[position].amount.toString()
        holder.binding.ingredientUnit.text = ingredientsList[position].unit
        holder.binding.ingredientConsistency.text = ingredientsList[position].consistency
        holder.binding.ingredientOriginal.text = ingredientsList[position].original
    }

    override fun getItemCount(): Int {
        return ingredientsList.size
    }

    fun setData(newData: List<ExtendedIngredient>) {
        val recipesDiffUtil = RecipesDiffUtil(ingredientsList, newData)
        val diffUtilResult = DiffUtil.calculateDiff(recipesDiffUtil)
        ingredientsList = newData
        diffUtilResult.dispatchUpdatesTo(this)
    }

}