package com.broadcast.recipeslistapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.broadcast.recipeslistapp.R
import com.broadcast.recipeslistapp.`interface`.OnRecipeListener
import com.broadcast.recipeslistapp.models.Recipe
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions


private const val RECIPE_TYPE = 1
private const val LOADING_TYPE = 2

class RecipeRecyclerAdapter(mOnRecipeListener: OnRecipeListener) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var mRecipes: List<Recipe>? = null
    private var mOnRecipeListener: OnRecipeListener? = null

    init {
        this.mOnRecipeListener = mOnRecipeListener

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        var view: View? = null
        when (viewType) {
            RECIPE_TYPE -> {
                view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.layout_recipe_list_item, parent, false)
                return RecipeViewHolder(view, mOnRecipeListener!!)
            }
            LOADING_TYPE -> {
                view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.layout_loading_list_item, parent, false)
                return LoadingViewHolder(view)
            }
            else -> {
                view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.layout_recipe_list_item, parent, false)
                return RecipeViewHolder(view, mOnRecipeListener!!)
            }
        }


    }

    override fun getItemCount(): Int {
        if (mRecipes.isNullOrEmpty()) {
            return 0
        } else {
            return mRecipes!!.size
        }
    }

    fun setRecipes(recipes: List<Recipe>) {
        mRecipes = recipes
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        val itemViewType = getItemViewType(position)
        if (itemViewType == RECIPE_TYPE) {
            val option = RequestOptions().centerCrop().error(R.drawable.ic_launcher_background)
            (holder as RecipeViewHolder).apply {
                //to load image from server
                Glide.with(itemView).setDefaultRequestOptions(option)
                    .load(mRecipes!![position].image_url)
                    .into(image!!)

                title!!.text = mRecipes!![position].title
                publisher!!.text = mRecipes!![position].publisher
                socialScore!!.text = "${Math.round(mRecipes!![position].social_rank)}"

            }
        }

    }

    fun displayLoading() {
        if (!isLoading()) {
            val recipe = Recipe()
            recipe.title = "LOADING..."
            val recipeList = arrayListOf<Recipe>()
            recipeList.add(recipe)
            mRecipes = recipeList
            notifyDataSetChanged()
        }
    }


    private fun isLoading(): Boolean {
        if (mRecipes!=null&& mRecipes!!.isNotEmpty()) {
            if (mRecipes!![mRecipes!!.size - 1].title == "LOADING...") {
                return true;
            }
        }
        return false

    }

    override fun getItemViewType(position: Int): Int {
        if (mRecipes!![position].title == "LOADING...") {
            return LOADING_TYPE;
        } else {
            return RECIPE_TYPE;
        }
    }
}