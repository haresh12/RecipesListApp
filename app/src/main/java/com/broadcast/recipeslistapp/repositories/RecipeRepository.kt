package com.broadcast.recipeslistapp.repositories

import com.broadcast.recipeslistapp.models.Recipe
import androidx.lifecycle.LiveData
import com.broadcast.recipeslistapp.requests.RecipeApiClient
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.MutableLiveData


object RecipeRepository {
     private var pageNumber = 0
    private var query = ""
    private val mRecipes = MediatorLiveData<MutableList<Recipe>>()
    private val mIsQueryExhausted = MutableLiveData<Boolean>()


    init {
        initMediators()
    }

    private fun initMediators() {
        val recipeListApiSource = RecipeApiClient.getRecipes()
        mRecipes.addSource(recipeListApiSource
        ) { recipes ->
            if (recipes != null) {
                mRecipes.value = recipes
                doneQuery(recipes)
            } else {
                doneQuery(null)

            }
        }

    }

    private fun doneQuery(recipeList: MutableList<Recipe>?) {
        if (recipeList!!.isNotEmpty()) {
            if (recipeList.size % 30 != 0) {
                mIsQueryExhausted.value = true
            }
        } else {
            mIsQueryExhausted.value = true
        }
    }

    fun isQueryExhausted(): LiveData<Boolean> {
        return mIsQueryExhausted
    }

    fun getRecipes(): LiveData<MutableList<Recipe>> {
        return mRecipes
    }

    fun getRecipe(): LiveData<Recipe> {
        return RecipeApiClient.getRecipe()
    }

    fun isNetworkTimedOut(): LiveData<Boolean> {
        return RecipeApiClient.isNetworkTimedOut()
    }

    fun searchRecipeById(recipeId: String) {
        RecipeApiClient.searchRecipeById(recipeId)
    }


    fun searchRecipeApi(query: String, pageNumber: Int) {
        val page = if (pageNumber == 0) {
            1
        } else {
            pageNumber
        }
        this.pageNumber = pageNumber
        this.query = query
        mIsQueryExhausted.value=false
        RecipeApiClient.searchRecipeApi(query, page)
    }

    fun cancleRequest() {
        RecipeApiClient.cancleRequest()
    }

    fun searchNextPage() {
        //TODO BUG HERE THIS METHOD IS GETTING CALLED MULTIPLE TIME
        searchRecipeApi(query, pageNumber + 1)

    }


}