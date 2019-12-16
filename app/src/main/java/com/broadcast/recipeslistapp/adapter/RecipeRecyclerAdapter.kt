package com.broadcast.recipeslistapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.broadcast.recipeslistapp.models.Recipe
import androidx.databinding.DataBindingUtil
import com.broadcast.recipeslistapp.R
import com.broadcast.recipeslistapp.com.broadcast.recipeslistapp.adapter.OnRecipeListener
import com.broadcast.recipeslistapp.databinding.LayoutCategoryListItemBinding
import com.broadcast.recipeslistapp.databinding.LayoutRecipeListItemBinding
import com.broadcast.recipeslistapp.util.Constants


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
                val binding: LayoutRecipeListItemBinding
                var layoutInflater: LayoutInflater? = null
                if (layoutInflater == null) {
                    layoutInflater = LayoutInflater.from(parent.context)
                }
                binding =
                    DataBindingUtil.inflate(
                        layoutInflater!!,
                        R.layout.layout_recipe_list_item,
                        parent,
                        false
                    )
                return RecipeViewHolder(binding, mOnRecipeListener!!)
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
                        R.layout.layout_search_exhausted,
                        parent,
                        false
                    )
                return SearchExhaustedViewHolder(view)
            }

            CATEGORY_TYPE -> {
                val binding: LayoutCategoryListItemBinding
                var layoutInflater: LayoutInflater? = null
                if (layoutInflater == null) {
                    layoutInflater = LayoutInflater.from(parent.context)
                }
                binding =
                    DataBindingUtil.inflate(
                        layoutInflater!!,
                        R.layout.layout_category_list_item,
                        parent,
                        false
                    )
                return CategoryViewHolder(binding, mOnRecipeListener!!)

            }
            else -> {
                val binding: LayoutRecipeListItemBinding
                var layoutInflater: LayoutInflater? = null
                if (layoutInflater == null) {
                    layoutInflater = LayoutInflater.from(parent.context)
                }
                binding =
                    DataBindingUtil.inflate(
                        layoutInflater!!,
                        R.layout.layout_recipe_list_item,
                        parent,
                        false
                    )
                return RecipeViewHolder(binding, mOnRecipeListener!!)
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
            (holder as RecipeViewHolder).apply {
                layoutRecipeListItemBinding.recipe = mRecipes!![position]
            }
        } else if (itemViewType == CATEGORY_TYPE) {
            (holder as CategoryViewHolder).apply {
                layoutCategoryListItemBinding.recipe = mRecipes!![position]
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