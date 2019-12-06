package com.broadcast.recipeslistapp.requests.responses

import com.broadcast.recipeslistapp.models.Recipe
import com.google.gson.annotations.SerializedName

data class RecipeResponse(
    @SerializedName("recipe")
    val recipe: Recipe? = null
)
