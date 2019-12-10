package com.broadcast.recipeslistapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.broadcast.recipeslistapp.`interface`.OnRecipeListener
import com.broadcast.recipeslistapp.models.Recipe
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import android.net.Uri
import android.opengl.Visibility
import com.broadcast.recipeslistapp.util.Constants
import kotlinx.android.synthetic.main.layout_loading_list_item.view.*


private const val RECIPE_TYPE = 1
private const val LOADING_TYPE = 2
private const val CATEGORY_TYPE = 3
private const val EXHAUSTED_TYPE = 4


class RecipeRecyclerAdapter(mOnRecipeListener: OnRecipeListener) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var mRecipes: MutableList<Recipe>? = null
    private var mOnRecipeListener: OnRecipeListener? = null

    init {
        this.mOnRecipeListener = mOnRecipeListener

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        var view: View? = null
        when (viewType) {
            RECIPE_TYPE -> {
                view = LayoutInflater.from(parent.context)
                    .inflate(
                        com.broadcast.recipeslistapp.R.layout.layout_recipe_list_item,
                        parent,
                        false
                    )
                return RecipeViewHolder(view, mOnRecipeListener!!)
            }
            LOADING_TYPE -> {
                view = LayoutInflater.from(parent.context)
                    .inflate(
                        com.broadcast.recipeslistapp.R.layout.layout_loading_list_item,
                        parent,
                        false
                    )
                return LoadingViewHolder(view)
            }
            EXHAUSTED_TYPE -> {
                view = LayoutInflater.from(parent.context)
                    .inflate(
                        com.broadcast.recipeslistapp.R.layout.layout_search_exhausted,
                        parent,
                        false
                    )
                return SearchExhaustedViewHolder(view)
            }

            CATEGORY_TYPE -> {
                view = LayoutInflater.from(parent.context)
                    .inflate(
                        com.broadcast.recipeslistapp.R.layout.layout_category_list_item,
                        parent,
                        false
                    )
                return CategoryViewHolder(view, mOnRecipeListener!!)
            }
            else -> {
                view = LayoutInflater.from(parent.context)
                    .inflate(
                        com.broadcast.recipeslistapp.R.layout.layout_recipe_list_item,
                        parent,
                        false
                    )
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

    fun setRecipes(recipes: MutableList<Recipe>) {
        mRecipes = recipes
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        val itemViewType = getItemViewType(position)
        if (itemViewType == RECIPE_TYPE) {
            val option = RequestOptions().centerCrop()
                .error(com.broadcast.recipeslistapp.R.drawable.ic_launcher_background)
            (holder as RecipeViewHolder).apply {
                //to load image from server
                Glide.with(itemView).setDefaultRequestOptions(option)
                    .load(mRecipes!![position].image_url)
                    .into(image!!)

                title!!.text = mRecipes!![position].title
                publisher!!.text = mRecipes!![position].publisher
                socialScore!!.text = "${Math.round(mRecipes!![position].social_rank)}"

            }
        } else if (itemViewType == CATEGORY_TYPE) {
            val path = Uri.parse(
                "android.resource://com.broadcast.recipeslistapp/drawable/" + mRecipes!![position].image_url
            )
            val option = RequestOptions().centerCrop()
                .error(com.broadcast.recipeslistapp.R.drawable.ic_launcher_background)
            (holder as CategoryViewHolder).apply {
                //to load image from server
                Glide.with(itemView).setDefaultRequestOptions(option)
                    .load(path)
                    .into(categoryImage!!)
                categoryTitle!!.text = mRecipes!![position].title

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

    fun displaySearchCategories() {
        val categories = arrayListOf<Recipe>()
        for (i in Constants.DEFAULT_SEARCH_CATEGORIES.indices) {
            val recipe = Recipe();
            recipe.title = Constants.DEFAULT_SEARCH_CATEGORIES[i]
            recipe.image_url = Constants.DEFAULT_SEARCH_CATEGORY_IMAGES[i]
            recipe.social_rank = -1f
            categories.add(recipe)
        }
        mRecipes = categories
        notifyDataSetChanged()

    }


    private fun isLoading(): Boolean {
        if (mRecipes != null && mRecipes!!.isNotEmpty()) {
            if (mRecipes!![mRecipes!!.size - 1].title == "LOADING...") {
                return true;
            }
        }
        return false

    }

    fun getRecipe(position: Int): Recipe {
        return mRecipes!![position]
    }

    fun setQueryExhausted() {
        hideLoading()
        val exhaustedRecipe = Recipe()
        exhaustedRecipe.title = "EXHAUSTED..."
        mRecipes!!.add(exhaustedRecipe)
        notifyDataSetChanged()
    }

    private fun hideLoading() {
        if (isLoading()) {
            for (recipe in mRecipes!!) {
                if (recipe.title == "LOADING...") {
                    mRecipes!!.remove(recipe)
                }
            }
            notifyDataSetChanged()
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (mRecipes!![position].social_rank == -1F) {
            CATEGORY_TYPE
        } else if (mRecipes!![position].title == "LOADING...") {
            LOADING_TYPE;
        } else if (position == mRecipes!!.size - 1 && position != 0 &&
            mRecipes!![position].title != "EXHAUSTED..."
        ) {
            LOADING_TYPE;
        } else if (mRecipes!![position].title == "EXHAUSTED...") {
            return EXHAUSTED_TYPE;
        } else {
            RECIPE_TYPE
        }
    }
}