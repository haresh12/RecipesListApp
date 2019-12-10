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
import com.broadcast.recipeslistapp.requests.responses.RecipeResponse


object RecipeApiClient {
    private val TAG = "RecipeApiClient"
    private val mRecipes: MutableLiveData<MutableList<Recipe>> = MutableLiveData()
    private val mRecipe: MutableLiveData<Recipe> = MutableLiveData()
    private val isNetworkTimedOut: MutableLiveData<Boolean> = MutableLiveData()

    private var mRetrieveRecipesRunnable: RetrieveRecipesRunnable? = null
    private var mRetrieveRecipeRunnable: RetrieveRecipeRunnable? = null

    fun getRecipes(): LiveData<MutableList<Recipe>> {
        return mRecipes
    }

    fun getRecipe(): LiveData<Recipe> {
        return mRecipe
    }

    fun isNetworkTimedOut(): LiveData<Boolean> {
        return isNetworkTimedOut
    }


    fun searchRecipeApi(query: String, pageNumber: Int) {
        if (mRetrieveRecipesRunnable != null) {
            mRetrieveRecipesRunnable = null
        }
        mRetrieveRecipesRunnable = RetrieveRecipesRunnable(query = query, pageNumber = pageNumber)
        val handler = AppExecutors.networkIO().submit(mRetrieveRecipesRunnable!!)

        // Set a timeout for the data refresh

        AppExecutors.networkIO().schedule({
            handler.cancel(true)
        }, NETWORK_TIMEOUT, TimeUnit.MILLISECONDS)

    }

    fun searchRecipeById(recipeId: String) {
        if (mRetrieveRecipeRunnable != null) {
            mRetrieveRecipeRunnable = null
        }
        isNetworkTimedOut.value = false
        mRetrieveRecipeRunnable = RetrieveRecipeRunnable(recipeId)
        val handler = AppExecutors.networkIO().submit(mRetrieveRecipeRunnable!!)

        // Set a timeout for the data refresh
        AppExecutors.networkIO().schedule({
            isNetworkTimedOut.postValue(true)
            handler.cancel(true);
        }, NETWORK_TIMEOUT, TimeUnit.MILLISECONDS)


    }


    class RetrieveRecipesRunnable(query: String, pageNumber: Int) : Runnable {
        private var query: String = ""
        private var pageNumber: Int = 0
        var cancelRequest: Boolean = false

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
                val recipeList = response.body()!!.recipes as MutableList<Recipe>
                if (pageNumber == 1) {
                    mRecipes.postValue(recipeList)
                } else {
                    val currentRecipes = mRecipes.value!!
                    currentRecipes.addAll(recipeList)
                    Log.e("currentRecipes size",currentRecipes.size.toString())
                    mRecipes.postValue(currentRecipes)
                }
            } else {
                val error = response.errorBody().toString()
                Log.e(TAG, "run: error: $error")
                mRecipes.postValue(null)
            }
        }

        private fun getRecipes(query: String, pageNumber: Int): Call<RecipeSearchResponse> {
            return ServiceGenerator.getRecipeApis().searchRecipe(
                query, pageNumber
            )
        }
    }

    class RetrieveRecipeRunnable(recipeId: String) : Runnable {
        private var recipeId: String = ""
        var cancelRequest: Boolean = false

        init {
            this.recipeId = recipeId
        }

        override fun run() {
            if (cancelRequest)
                return
            val response = getRecipe(recipeId).execute()
            if (response != null && response.isSuccessful) {
                val recipe = response.body()!!.recipe
                mRecipe.postValue(recipe)
            } else {
                val error = response.errorBody().toString()
                Log.e(TAG, "run: error: " + error);
                mRecipes.postValue(null);
            }
        }

        private fun getRecipe(recipeId: String): Call<RecipeResponse> {
            return ServiceGenerator.getRecipeApis().getRecipe(
                recipeId
            )
        }
    }


    fun cancleRequest() {
        if (mRetrieveRecipesRunnable != null) {
            mRetrieveRecipesRunnable!!.cancelRequest = true
        }
        if (mRetrieveRecipeRunnable != null) {
            mRetrieveRecipeRunnable!!.cancelRequest = true
        }
    }


}