package com.broadcast.recipeslistapp.requests.responses

import com.broadcast.recipeslistapp.models.Recipe
import com.google.gson.annotations.SerializedName

data class RecipeSearchResponse(
    @SerializedName("count")
    val count: Int = 0,
    @SerializedName("recipes")
    val recipes: ArrayList<Recipe>? = null
)
