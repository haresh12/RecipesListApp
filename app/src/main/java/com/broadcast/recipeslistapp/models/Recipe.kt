package com.broadcast.recipeslistapp.models

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
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
    var image_url: String = "",
    @SerializedName("social_rank")
    @Bindable
    var social_rank: Float = 0.0f
) : Serializable, BaseObservable() {

    fun setSocialRank(): String {
        return "${Math.round(social_rank)}"
    }

}

