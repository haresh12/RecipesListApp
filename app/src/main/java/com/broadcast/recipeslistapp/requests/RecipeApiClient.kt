package com.broadcast.recipeslistapp.requests

import android.util.Log
import com.broadcast.recipeslistapp.models.Recipe
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.LiveData
import com.broadcast.recipeslistapp.AppExecutors
import com.broadcast.recipeslistapp.requests.responses.RecipeSearchResponse
import com.broadcast.recipeslistapp.util.Constants
import com.broadcast.recipeslistapp.util.Constants.NETWORK_TIMEOUT
import retrofit2.Call
import java.util.concurrent.TimeUnit;
import com.broadcast.recipeslistapp.requests.RecipeApiClient.RetrieveRecipesRunnable


class RecipeApiClient {
    private val TAG = "RecipeApiClient"
    private val mRecipes: MutableLiveData<MutableList<Recipe>> = MutableLiveData()
    private var mRetrieveRecipesRunnable: RetrieveRecipesRunnable? = null

    private val appExecutors: AppExecutors = AppExecutors()
    fun getRecipes(): LiveData<MutableList<Recipe>> {
        return mRecipes
    }


    fun searchRecipeApi(query: String, pageNumber: Int) {
        if (mRetrieveRecipesRunnable != null) {
            mRetrieveRecipesRunnable = null
        }
        mRetrieveRecipesRunnable = RetrieveRecipesRunnable(query = query, pageNumber = pageNumber)
        val handler = appExecutors.networkIO().submit(mRetrieveRecipesRunnable!!)

        // Set a timeout for the data refresh

        appExecutors.networkIO().schedule({
            handler.cancel(true);
        }, NETWORK_TIMEOUT, TimeUnit.MILLISECONDS)

    }

    inner class RetrieveRecipesRunnable(query: String, pageNumber: Int) : Runnable {
        private var query: String = ""
        private var pageNumber: Int = 0
        private var cancelRequest: Boolean = false

        init {
            this.query = query
            this.pageNumber = pageNumber
            cancelRequest = false
        }
        override fun run() {
            if (cancelRequest)
                return

            val response = getRecipes(query, pageNumber).execute()
            if (response != null && response.isSuccessful) {
                val recipeList = response.body()!!.recipes
                if (pageNumber == 1) {
                    mRecipes.postValue(recipeList)
                } else {
                    val currentRecipes: MutableList<Recipe> = mRecipes.value!!
                    currentRecipes.addAll(currentRecipes)
                    mRecipes.postValue(currentRecipes)
                }
            } else {
                val error = response.errorBody().toString()
                Log.e(TAG, "run: error: " + error);
                mRecipes.postValue(null);
            }
        }

        private fun getRecipes(query: String, pageNumber: Int): Call<RecipeSearchResponse> {
            return ServiceGenerator.getRecipeApis().searchRecipe(
                query, pageNumber
            )
        }


    }

}