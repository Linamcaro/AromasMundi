package com.example.aromasmundi.ui.fragments.feed

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.datastore.preferences.protobuf.Api
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.aromasmundi.MainViewModel
import com.example.aromasmundi.R
import com.example.aromasmundi.adapters.RecipesAdapter
import com.example.aromasmundi.databinding.FragmentFeedBinding
import com.example.aromasmundi.util.Constants
import com.example.aromasmundi.util.Constants.Companion.apiKey
import com.example.aromasmundi.util.NetworkResult
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class FeedFragment : Fragment() {
    private  val recipesAdapter by lazy { RecipesAdapter() }
    private lateinit var recipesBinding: FragmentFeedBinding
    private lateinit var mainViewModel: MainViewModel
            override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
                recipesBinding = DataBindingUtil.inflate(inflater,
                    R.layout.fragment_feed, container, false)

                mainViewModel = ViewModelProvider(requireActivity())[MainViewModel::class.java]
                recipesBinding.lifecycleOwner = this

                setUpRecyclerView()
                requestApiData()

                return recipesBinding.root
    }

    private fun requestApiData(){
        mainViewModel.getRecipes(applyQueries())
        mainViewModel.recipesResponse.observe(viewLifecycleOwner){response ->

            when(response){
                is NetworkResult.Success ->{
                    hideShimmerEffect()
                    response.data?.let{
                        recipesAdapter.setData(it)
                    }
                }
                is NetworkResult.Error ->{
                    hideShimmerEffect()
                    Toast.makeText(
                        requireContext(),
                        response.message.toString(),
                        Toast.LENGTH_LONG)
                        .show()
                }

                is NetworkResult.Loading ->{
                    showShimmerEffect()
                }
            }

        }

    }

    //Set Up the Queries
    private fun applyQueries(): HashMap<String, String>{
        val queries: HashMap<String, String> = HashMap()

        queries["number"] = "50"
        queries["apiKey"] = apiKey
        queries["type"] = "snack"
        queries["diet"] = "vegan"
        queries["addRecipeInformation"] = "true"
        queries["fillIngredients"] = "true"

        return queries
    }

    //Set up the RecyclerView
    private fun setUpRecyclerView(){
        recipesBinding.recyclerView.adapter = recipesAdapter
        recipesBinding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        showShimmerEffect()
    }

    private fun showShimmerEffect(){
        recipesBinding.shimmerFrameLayout.startShimmer()
    }

    private fun hideShimmerEffect(){
        recipesBinding.shimmerFrameLayout.stopShimmer()
    }
}