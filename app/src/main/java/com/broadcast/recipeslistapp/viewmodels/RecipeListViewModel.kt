package com.broadcast.recipeslistapp.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.broadcast.recipeslistapp.models.Recipe
import com.broadcast.recipeslistapp.repositories.RecipeRepository
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.icu.lang.UCharacter.GraphemeClusterBreak.T


class RecipeListViewModel : ViewModel() {
    private var mIsViewingRecipes: Boolean = false
    private var isPerformingQuery = false

    fun getRecipes(): LiveData<MutableList<Recipe>> {
        return RecipeRepository.getRecipes()
    }

    fun isQueryExhausted(): LiveData<Boolean> {
        return RecipeRepository.isQueryExhausted()
    }

    fun searchRecipeApi(query: String, pageNumber: Int) {
        mIsViewingRecipes = true
        isPerformingQuery = true
        RecipeRepository.searchRecipeApi(query, pageNumber)
    }

    fun isPerformingQuery(): Boolean {
        return isPerformingQuery
    }

    fun searchNextPage() {
        if (!isPerformingQuery && isViewingRecipes() && !isQueryExhausted().value!!) {
            RecipeRepository.searchNextPage()
        }
    }


    fun setPerformingQuery(isPerformingQuery: Boolean) {
        this.isPerformingQuery = isPerformingQuery
    }


    fun isViewingRecipes(): Boolean {
        return mIsViewingRecipes
    }

    fun setViewingRecipe(isViewingRecipe: Boolean) {
        mIsViewingRecipes = isViewingRecipe
    }

    fun onBackPressed(): Boolean {
        if (isPerformingQuery()) {
            RecipeRepository.cancleRequest()
            return true
        }
        return false
    }


}