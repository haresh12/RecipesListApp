package com.broadcast.recipeslistapp.requests

import com.broadcast.recipeslistapp.util.Constants
import com.google.gson.Gson
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ServiceGenerator {


    private val interceptor = Interceptor { chain ->
        val url =
            chain.request().url.newBuilder().addQueryParameter(Constants.API_KEY, "").build()
        val request = chain.request()
            .newBuilder()
            .url(url)
            .build()
        chain.proceed(request)
    }

    private val apiClient = OkHttpClient().newBuilder()
        .addInterceptor(HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        })
        .addInterceptor(interceptor).build()


    val retrofitBuilder = Retrofit.Builder()
        .baseUrl(Constants.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(apiClient)
        .build()

    val recipeApi = retrofitBuilder.create(RecipeApi::class.java)

    fun getRecipeApis(): RecipeApi {
        return recipeApi
    }


}