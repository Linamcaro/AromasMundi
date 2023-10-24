package com.example.aromasmundi

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities.TRANSPORT_CELLULAR
import android.net.NetworkCapabilities.TRANSPORT_ETHERNET
import android.net.NetworkCapabilities.TRANSPORT_WIFI
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.aromasmundi.data.Repository
import com.example.aromasmundi.models.RecipeResponse
import com.example.aromasmundi.util.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import retrofit2.Response


@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: Repository,
    application: Application): AndroidViewModel(Application()) {

    /** RETROFIT */
    //Store the response from the API
    var recipesResponse: MutableLiveData<NetworkResult<RecipeResponse>> = MutableLiveData()

    fun getRecipes(queries: Map<String, String>) = viewModelScope.launch{
        getRecipesSafeCall(queries)
    }

    //Request the API data
    private suspend fun getRecipesSafeCall(queries: Map<String, String>) {

        recipesResponse.value = NetworkResult.Loading()

        if(hasInternetConnection()) {
            try {
                val response = repository.remote.getRecipes(queries)
                recipesResponse.value = handleRecipesResponse(response)

            }catch (e: Exception){

                recipesResponse.value = NetworkResult.Error("Recipes not found")

            }
        }else{
            recipesResponse.value = NetworkResult.Error("No Internet Connection")
        }

    }

    //Handle the error or successful responses from the API
    private fun handleRecipesResponse(response: Response<RecipeResponse>): NetworkResult<RecipeResponse>? {

        when{

            //when it takes long for the API to respond to the request
            response.message().toString().contains("timeout") -> {
                return NetworkResult.Error("Timeout")
            }

            //when the API key gets to its limit
            response.code() == 402 -> {
                return NetworkResult.Error("API Key Limited")
            }

            //when the API result is empty
            response.body()!!.results.isEmpty() -> {
                return NetworkResult.Error("Recepies not found")
            }

            response.isSuccessful -> {
                val recipes = response.body()
                return NetworkResult.Success(recipes!!)
            }
            else -> {
                return NetworkResult.Error(response.message())
            }
        }


    }


    //Check for internet connection
        private fun hasInternetConnection(): Boolean {
            val connectivityManager = getApplication<Application>().getSystemService(
                Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val activeNetwork = connectivityManager.activeNetwork ?: return false
            val capabilities = connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false
            return when{
                capabilities.hasTransport(TRANSPORT_WIFI) -> true
                capabilities.hasTransport(TRANSPORT_CELLULAR) -> true
                capabilities.hasTransport(TRANSPORT_ETHERNET) -> true
                else -> false
            }

        }
}