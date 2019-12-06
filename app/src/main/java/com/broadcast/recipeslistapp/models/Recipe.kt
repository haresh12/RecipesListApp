package com.broadcast.recipeslistapp.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Recipe(
    @SerializedName("title")
    var title: String = "",
    @SerializedName("publisher")
    val publisher: String = "",
    @SerializedName("publisher_url")
    val publisher_url: String = "",
    @SerializedName("ingredients")
    val ingredients: Array<String>? = null,
    @SerializedName("recipe_id")
    val recipe_id: String = "",
    @SerializedName("image_url")
    val image_url: String = "",
    @SerializedName("social_rank")
    val social_rank: Float = 0.0f
) : Serializable
