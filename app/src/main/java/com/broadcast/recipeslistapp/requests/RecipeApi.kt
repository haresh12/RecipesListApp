package com.broadcast.recipeslistapp.requests

import com.broadcast.recipeslistapp.requests.responses.RecipeResponse
import com.broadcast.recipeslistapp.requests.responses.RecipeSearchResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RecipeApi {


    //SEARCH
    @GET("api/search")
    fun searchRecipe(
        @Query("q") query: String,
        @Query("page") page: Int
    ): Call<RecipeSearchResponse>

    //GET RECIPE REQUEST

    @GET("api/get")
    fun getRecipe(
        @Query("rId") recipe_id: String
     ): Call<RecipeResponse>


}