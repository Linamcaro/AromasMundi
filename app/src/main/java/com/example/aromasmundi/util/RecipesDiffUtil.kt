package com.example.aromasmundi.util

import androidx.recyclerview.widget.DiffUtil
import com.example.aromasmundi.models.Result

    class RecipesDiffUtil(
        private val oldList: List<Result>,
        private val newList: List<Result>
    ): DiffUtil.Callback() {

        //return size of the old list
        override fun getOldListSize(): Int {
            return oldList.size
        }

        //return size of the new list
        override fun getNewListSize(): Int {
            return newList.size
        }

        //called to decide whether two items are the same (represent the same item)
        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition].id === newList[newItemPosition].id
        }

        //called to decide whether two items have the same data
        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition] == newList[newItemPosition]
        }
    }