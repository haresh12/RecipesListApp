package com.broadcast.recipeslistapp.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.broadcast.recipeslistapp.models.Recipe
import com.broadcast.recipeslistapp.repositories.RecipeRepository


class RecipeListViewModel() : ViewModel() {
    private val mRecipeRepository: RecipeRepository = RecipeRepository()

    fun getRecipes(): LiveData<MutableList<Recipe>> {
        return mRecipeRepository.getRecipes()
    }

    fun searchRecipeApi(query: String, pageNumber: Int) {
        mRecipeRepository.searchRecipeApi(query, pageNumber)
    }


}