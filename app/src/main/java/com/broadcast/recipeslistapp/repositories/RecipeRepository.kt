package com.broadcast.recipeslistapp.repositories

import com.broadcast.recipeslistapp.models.Recipe
import androidx.lifecycle.LiveData
import com.broadcast.recipeslistapp.requests.RecipeApiClient


class RecipeRepository {
    private val mRecipeApiClient: RecipeApiClient = RecipeApiClient()

    fun getRecipes(): LiveData<MutableList<Recipe>> {
        return mRecipeApiClient.getRecipes()
    }

    fun searchRecipeApi(query: String, pageNumber: Int) {
        val page = if (pageNumber == 0) {
            1
        } else {
            pageNumber
        }
        mRecipeApiClient.searchRecipeApi(query, page)
    }

}