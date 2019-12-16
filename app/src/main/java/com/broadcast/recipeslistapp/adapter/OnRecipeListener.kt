package com.broadcast.recipeslistapp.com.broadcast.recipeslistapp.adapter

interface OnRecipeListener {
    fun onCategoryClick(category :String)
    fun onRecipeClick(position: Int)

}