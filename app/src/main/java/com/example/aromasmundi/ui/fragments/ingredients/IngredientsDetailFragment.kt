package com.example.aromasmundi.ui.fragments.ingredients

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.aromasmundi.R
import com.example.aromasmundi.adapters.IngredientsAdapter
import com.example.aromasmundi.databinding.FragmentIngredientsDetailBinding
import com.example.aromasmundi.models.Result
import com.example.aromasmundi.util.Constants.Companion.RECIPE_RESULT_KEY
import com.example.aromasmundi.util.retrieveParcelable

class IngredientsDetailFragment : Fragment() {

    private lateinit var ingredientsBinding: FragmentIngredientsDetailBinding
    private val ingredientsAdapter: IngredientsAdapter by lazy { IngredientsAdapter() }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {

        ingredientsBinding = DataBindingUtil.inflate(inflater,
            R.layout.fragment_ingredients_detail, container, false)


        val args = arguments
        val myBundle: Result? = args?.retrieveParcelable(RECIPE_RESULT_KEY)

        setUpRecyclerView()
        myBundle?.extendedIngredients?.let {
            ingredientsAdapter.setData(it)
        }

        return ingredientsBinding.root
    }

    private fun setUpRecyclerView() {
        ingredientsBinding.ingredientsRecyclerview.adapter = ingredientsAdapter
        ingredientsBinding.ingredientsRecyclerview.layoutManager= LinearLayoutManager(requireContext())
    }

}