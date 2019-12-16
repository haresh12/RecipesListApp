package com.broadcast.recipeslistapp

import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.broadcast.recipeslistapp.models.Recipe
import com.broadcast.recipeslistapp.viewmodels.RecipeViewModel
import com.bumptech.glide.request.RequestOptions

import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_recipe.*
import kotlin.math.roundToInt

import android.view.View


private const val TAG = "RecipeActivity"

class RecipeActivity : BaseActivity() {
    private lateinit var mRecipeViewModel: RecipeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.broadcast.recipeslistapp.R.layout.activity_recipe)
        showProgressBar(true)
        mRecipeViewModel = ViewModelProvider(this).get(RecipeViewModel::class.java)
        getIncomingIntent()
        subscribeObservers()
    }

    private fun getIncomingIntent() {
        if (intent.hasExtra("recipe")) {
            val recipe = intent.getSerializableExtra("recipe") as Recipe
            mRecipeViewModel.searchRecipeById(recipe.recipe_id)
        }
    }

    //Observe the recipe list changes from viewModel
    private fun subscribeObservers() {
        mRecipeViewModel.getRecipe().observe(this, Observer<Recipe> {
            if (it != null)
                setRecipeProperties(it)
            mRecipeViewModel.setRetrievedRecipe(true)

        })

        mRecipeViewModel.isNetworkTimedOut().observe(this, Observer<Boolean> {
            if (it && !mRecipeViewModel.didRetrieveRecipe()) {
                displayErrorScreen("Error retrieving data. Check network connection.")
            }
        })

    }

    private fun setRecipeProperties(recipe: Recipe) {
        val requestOptions = RequestOptions()
            .placeholder(R.drawable.ic_launcher_background)
        // set image with glide
        recipe.apply {
            Glide.with(this@RecipeActivity)
                .setDefaultRequestOptions(requestOptions)
                .load(image_url)
                .into(mRecipeImage)

            mRecipeTitle.text = title
            mRecipeRank.text = (social_rank.roundToInt()).toString()

            mRecipeIngredientsContainer.removeAllViews()

            ingredients?.forEach {
                val txtView = TextView(this@RecipeActivity)
                txtView.text = it
                txtView.textSize = 15f
                txtView.layoutParams = LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )
                mRecipeIngredientsContainer.addView(txtView);
            }
            showParent();
            showProgressBar(false);
        }

    }

    private fun showParent() {
        mScrollView.visibility = View.VISIBLE
    }


    private fun displayErrorScreen(errorMessage: String) {
        mRecipeTitle.text = "Error retrieving recipe..."
        mRecipeRank.text = ""
        val textView = TextView(this)
        if (errorMessage != "") {
            textView.text = errorMessage
        } else {
            textView.text = "Error"
        }
        textView.textSize = 15f
        textView.layoutParams = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT
        )
        mRecipeIngredientsContainer.addView(textView);

        val requestOptions = RequestOptions()
            .placeholder(R.drawable.ic_launcher_background)
        Glide.with(this@RecipeActivity)
            .setDefaultRequestOptions(requestOptions)
            .load(R.drawable.ic_launcher_background)
            .into(mRecipeImage)

        showParent();
        showProgressBar(false);

    }


}
