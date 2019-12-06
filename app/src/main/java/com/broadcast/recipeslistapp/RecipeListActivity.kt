package com.broadcast.recipeslistapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import android.widget.SearchView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.broadcast.recipeslistapp.`interface`.OnRecipeListener
import com.broadcast.recipeslistapp.adapter.RecipeRecyclerAdapter
import com.broadcast.recipeslistapp.requests.ServiceGenerator
import com.broadcast.recipeslistapp.requests.responses.RecipeSearchResponse
import com.broadcast.recipeslistapp.util.Constants
import kotlinx.android.synthetic.main.activity_base.*
import kotlinx.android.synthetic.main.activity_recipe_list.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import com.broadcast.recipeslistapp.models.Recipe
import com.broadcast.recipeslistapp.requests.responses.RecipeResponse
import com.broadcast.recipeslistapp.viewmodels.RecipeListViewModel
import java.io.IOException


class RecipeListActivity : BaseActivity(), OnRecipeListener {

    private val TAG = "RecipeListActivity"
    private lateinit var mRecipeListViewModel: RecipeListViewModel
    private var mAdapter: RecipeRecyclerAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipe_list)

        mRecipeListViewModel = ViewModelProvider(this).get(RecipeListViewModel::class.java)
        initRecyclerView()
        subscribeObservers()
        initSearchView()

    }

    private fun initRecyclerView() {
        mAdapter = RecipeRecyclerAdapter(this)
        recipe_list.adapter = mAdapter
        recipe_list.layoutManager = LinearLayoutManager(this)
    }


    //Observe the recipe list changes from viewModel
    private fun subscribeObservers() {
         mRecipeListViewModel.getRecipes().observe(this, Observer<List<Recipe>> {
            mAdapter?.setRecipes(it)

        })

    }

    private fun initSearchView() {
        search_view.setOnQueryTextListener(object :
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(newText: String?): Boolean {
                mAdapter?.displayLoading()
                mRecipeListViewModel.searchRecipeApi(newText!!, 1);
                return false
            }

            override fun onQueryTextChange(quary: String?): Boolean {
                return false
            }

        })


    }


    override fun onRecipeClick(position: Int) {
    }

    override fun onCategoryClick(category: String) {
    }


//    private fun testSearchRetrofitRequest() {
//        val recipeApi = ServiceGenerator.getRecipeApis()
//        val responseCall: Call<RecipeSearchResponse> = recipeApi.searchRecipe(
//            "chicken", 1
//        )
//        responseCall.enqueue(object : Callback<RecipeSearchResponse> {
//            override fun onResponse(
//                call: Call<RecipeSearchResponse>,
//                response: Response<RecipeSearchResponse>
//            ) {
//                if (response.isSuccessful) {
//                    val recipes: ArrayList<Recipe> = response.body()?.recipes!!
//                    recipes.forEach {
//                        Log.d(TAG, "onResponse: $it");
//                    }
//                } else {
//                    try {
//                        Log.d(TAG, "onResponse: " + response.errorBody().toString());
//                    } catch (e: IOException) {
//                        e.printStackTrace();
//                    }
//
//                }
//            }
//
//            override fun onFailure(call: Call<RecipeSearchResponse>, t: Throwable) {
//                Log.d(TAG, "onResponse: ERROR: " + t.message);
//            }
//
//        })
//
//    }
//
//    private fun testGetRecipe() {
//        val recipeApi = ServiceGenerator.getRecipeApis()
//        val responseCall: Call<RecipeResponse> = recipeApi.getRecipe(
//            recipe_id = "35382"
//        )
//
//        responseCall.enqueue(object : Callback<RecipeResponse> {
//            override fun onResponse(
//                call: Call<RecipeResponse>,
//                response: Response<RecipeResponse>
//            ) {
//                if (response.isSuccessful) {
//                    val recipe = response.body()!!.recipe
//                    Log.d(TAG, "onResponse: " + recipe.toString());
//                } else {
//                    try {
//                        Log.d(TAG, "onResponse: " + response.errorBody().toString())
//                    } catch (e: IOException) {
//                        e.printStackTrace()
//                    }
//
//                }
//
//            }
//
//            override fun onFailure(call: Call<RecipeResponse>, t: Throwable) {
//                Log.d(TAG, "onResponse: ERROR: " + t.message);
//
//            }
//
//        })
//
//    }


}
