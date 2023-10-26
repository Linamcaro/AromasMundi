package com.example.aromasmundi.bindingadapters

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import coil.load
import com.example.aromasmundi.R


class RecipesRowBinding {
    companion object{

        //Load recipe image
        @BindingAdapter("loadRecipeImage")
        @JvmStatic
        fun loadImageUrl(imageView: ImageView, imageUrl: String){
            imageView.load(imageUrl) {
                crossfade(600)
                error(R.drawable.ic_error_placeholder)

            }
        }

        //Change the vegan image and text color

        @BindingAdapter("setVeganColor")
        @JvmStatic
        fun setVeganColor(view: View, vegan: Boolean){
            if(vegan){
                when(view){
                    is TextView -> {
                        view.setTextColor(
                            ContextCompat.getColor(
                                view.context,R.color.green))
                    }

                    is ImageView -> {
                        view.setColorFilter(
                            ContextCompat.getColor(
                                view.context,R.color.green))
                    }
                }
            }
        }

    }
}