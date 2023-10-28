package com.example.aromasmundi.ui.fragments.feed

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.aromasmundi.viewmodels.MainViewModel
import com.example.aromasmundi.R
import com.example.aromasmundi.adapters.RecipesAdapter
import com.example.aromasmundi.databinding.FragmentFeedBinding
import com.example.aromasmundi.util.NetworkResult
import com.example.aromasmundi.util.observeOnce
import com.example.aromasmundi.viewmodels.RecipesViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class FeedFragment : Fragment() {
    private  val recipesAdapter by lazy { RecipesAdapter() }
    private lateinit var recipesBinding: FragmentFeedBinding
    private lateinit var mainViewModel: MainViewModel
    private lateinit var recipesViewModel: RecipesViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mainViewModel = ViewModelProvider(requireActivity())[MainViewModel::class.java]
        recipesViewModel = ViewModelProvider(requireActivity())[RecipesViewModel::class.java]

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        recipesBinding = DataBindingUtil.inflate(inflater,
            R.layout.fragment_feed, container, false)

        recipesBinding.lifecycleOwner = this

        setUpRecyclerView()
        readDatabase()

        recipesBinding.recipesFab.setOnClickListener {
            findNavController().navigate(R.id.action_feedFragment_to_recipesBottomSheet)
        }

        return recipesBinding.root
    }

    //Set up the RecyclerView
    private fun setUpRecyclerView(){
        recipesBinding.recyclerView.adapter = recipesAdapter
        recipesBinding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        showShimmerEffect()
    }

    //Request for the data to be displayed from the database
    private fun readDatabase() {
        lifecycleScope.launch {
            mainViewModel.readRecipes.observeOnce(viewLifecycleOwner){database ->

                if(database.isNotEmpty()){
                    Log.d("RecipesFragment", "readDatabase called ")
                    recipesAdapter.setData(database[0].foodRecipe)
                    hideShimmerEffect()
                } else{
                    requestApiData()
                }
            }
        }
    }

    private fun requestApiData(){
        Log.d("RecipesFragment", "requestApiData called ")
        mainViewModel.getRecipes(recipesViewModel.applyQueries())
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
                    loadDataFromCache()
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

    private fun loadDataFromCache(){
        lifecycleScope.launch {
            mainViewModel.readRecipes.observe(viewLifecycleOwner){ database ->

                if (database.isNotEmpty()) {
                    Log.d("RecipesFragment", "readDatabase called ")
                    recipesAdapter.setData(database[0].foodRecipe)
                }
            }
        }
    }


    private fun showShimmerEffect(){
        recipesBinding.shimmerFrameLayout.startShimmer()
    }

    private fun hideShimmerEffect(){
        recipesBinding.shimmerFrameLayout.stopShimmer()
    }

}