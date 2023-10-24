package com.example.aromasmundi.bindingadapters

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.example.aromasmundi.R
import com.example.aromasmundi.databinding.RecipesRowLayoutBinding

class RecipesRowBinding {
    companion object{

        //Convert number of likes to string
        @BindingAdapter("setNumberOfLikes")
        @JvmStatic
        fun setNumberOfLikes(textView: TextView, likes: Int){
            textView.text = likes.toString()
        }

        //Convert number of minutes to string
        @BindingAdapter("setNumberOfMinutes")
        @JvmStatic
        fun setNumberOfMinutes(textView: TextView, minutes: Int){
            textView.text = minutes.toString()
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