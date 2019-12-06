package com.broadcast.recipeslistapp.`interface`

interface OnRecipeListener {

    fun onRecipeClick(position: Int)

    fun onCategoryClick(category: String)
}