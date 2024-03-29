package com.example.aromasmundi.ui.fragments.feed

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.aromasmundi.viewmodels.MainViewModel
import com.example.aromasmundi.R
import com.example.aromasmundi.adapters.RecipesAdapter
import com.example.aromasmundi.databinding.FragmentFeedBinding
import com.example.aromasmundi.util.NetworkListener
import com.example.aromasmundi.util.NetworkResult
import com.example.aromasmundi.util.observeOnce
import com.example.aromasmundi.viewmodels.RecipesViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class FeedFragment : Fragment(), SearchView.OnQueryTextListener {

    private lateinit var recipesBinding: FragmentFeedBinding
    private  val recipesAdapter by lazy { RecipesAdapter() }
    private lateinit var mainViewModel: MainViewModel
    private lateinit var recipesViewModel: RecipesViewModel
    private lateinit var networkListener: NetworkListener

    private val args by navArgs<FeedFragmentArgs>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mainViewModel = ViewModelProvider(requireActivity())[MainViewModel::class.java]
        recipesViewModel = ViewModelProvider(requireActivity())[RecipesViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        // Inflate the layout for this fragment
        recipesBinding = DataBindingUtil.inflate(inflater,
            R.layout.fragment_feed, container, false)

        recipesBinding.lifecycleOwner = this

        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(object : MenuProvider {

            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.recipes_menu, menu)

                val search = menu.findItem(R.id.menu_search)
                val searchView = search.actionView as? SearchView
                searchView?.isSubmitButtonEnabled = true
                searchView?.setOnQueryTextListener(this@FeedFragment)

            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return true
            }
        })

        setUpRecyclerView()

        recipesViewModel.readBackOnline.observe(viewLifecycleOwner){
            recipesViewModel.backOnline = it
        }

        lifecycleScope.launch {
            networkListener = NetworkListener()
            networkListener.checkNetworkAvailability(requireContext())
                .collect{status ->
                    Log.d("NetworkListener", status.toString())

                    recipesViewModel.networkStatus = status
                    recipesViewModel.showNetworkStatus()
                    readDatabase()
                }
        }

        recipesBinding.recipesFab.setOnClickListener {
            if(recipesViewModel.networkStatus){
                findNavController().navigate(R.id.action_feedFragment_to_recipesBottomSheet)
            }else{
                recipesViewModel.showNetworkStatus()
            }
        }

        return recipesBinding.root
    }

    //Set up the RecyclerView
    private fun setUpRecyclerView(){
        recipesBinding.recyclerView.adapter = recipesAdapter
        recipesBinding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        showShimmerEffect()
    }

    //This will be called when the search button is pressed
    override fun onQueryTextSubmit(query: String?): Boolean {
        if(query != null){
            searchApiData(query)
        }
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        return true
    }

    //Request for the data to be displayed from the database
    private fun readDatabase() {
        lifecycleScope.launch {
            mainViewModel.readRecipes.observeOnce(viewLifecycleOwner){database ->

                if(database.isNotEmpty() && !args.backFromBottomSheet){
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
                        Toast.LENGTH_SHORT)
                        .show()
                }

                is NetworkResult.Loading ->{
                    showShimmerEffect()
                }
            }
        }
    }

    private fun searchApiData(searchQuery: String){
        showShimmerEffect()
        mainViewModel.searchRecipes(recipesViewModel.applySearchQuery(searchQuery))
        mainViewModel.searchRecipesResponse.observe(viewLifecycleOwner){response ->
            when(response) {
                is NetworkResult.Success ->{
                    hideShimmerEffect()
                    val foodrecipe = response.data
                    foodrecipe?.let{
                        recipesAdapter.setData(it)
                    }
                }
                is NetworkResult.Error ->{
                    hideShimmerEffect()
                    loadDataFromCache()
                    Toast.makeText(
                        requireContext(),
                        response.message.toString(),
                        Toast.LENGTH_SHORT)
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
        recipesBinding.shimmerFrameLayout.visibility = View.VISIBLE
        recipesBinding.recyclerView.visibility = View.GONE
    }

    private fun hideShimmerEffect(){
        recipesBinding.shimmerFrameLayout.stopShimmer()
        recipesBinding.shimmerFrameLayout.visibility = View.GONE
        recipesBinding.recyclerView.visibility = View.VISIBLE
    }

}