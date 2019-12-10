package com.broadcast.recipeslistapp.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.broadcast.recipeslistapp.models.Recipe
import com.broadcast.recipeslistapp.repositories.RecipeRepository



class RecipeViewModel : ViewModel() {
     private var mDidRetrieveRecipe: Boolean = false

    fun getRecipe(): LiveData<Recipe> {
        return RecipeRepository.getRecipe()
    }

    fun searchRecipeById(recipeId: String) {
        RecipeRepository.searchRecipeById(recipeId)
    }

    fun isNetworkTimedOut(): LiveData<Boolean> {
        return RecipeRepository.isNetworkTimedOut()
    }

    fun setRetrievedRecipe(retrievedRecipe: Boolean) {
        mDidRetrieveRecipe = retrievedRecipe
    }

    fun didRetrieveRecipe(): Boolean {
        return mDidRetrieveRecipe
    }


}
