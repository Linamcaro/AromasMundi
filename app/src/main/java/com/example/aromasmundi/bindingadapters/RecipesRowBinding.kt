package com.example.aromasmundi.bindingadapters

import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import androidx.navigation.findNavController
import coil.load
import com.example.aromasmundi.R
import com.example.aromasmundi.models.Result
import com.example.aromasmundi.ui.fragments.feed.FeedFragmentDirections
import org.jsoup.Jsoup


class RecipesRowBinding {
    companion object {
        @BindingAdapter("onRecipeClickListener")
        @JvmStatic
        fun onRecipeClickListener(recipeRowLayout: ConstraintLayout, result: Result)
        {
            recipeRowLayout.setOnClickListener {
                try {
                    val action = FeedFragmentDirections.actionFeedFragmentToDetailsActivity(result)
                    recipeRowLayout.findNavController().navigate(action)
                } catch (e: Exception) {
                    Log.d("RecipesRowBinding", e.message.toString())
                }
            }
        }

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

        @BindingAdapter("parseHtml")
        @JvmStatic
        fun parseHtml(textView: TextView, description:String?){
            if(description != null){
                val desc = Jsoup.parse(description).text()
                textView.text = desc

            }
        }
    }

}