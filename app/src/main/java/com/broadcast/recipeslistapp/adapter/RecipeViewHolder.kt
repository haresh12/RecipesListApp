package com.broadcast.recipeslistapp.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.broadcast.recipeslistapp.com.broadcast.recipeslistapp.adapter.OnRecipeListener
import com.broadcast.recipeslistapp.databinding.LayoutRecipeListItemBinding


class RecipeViewHolder(val layoutRecipeListItemBinding: LayoutRecipeListItemBinding, onRecipeListener: OnRecipeListener) :
    RecyclerView.ViewHolder(layoutRecipeListItemBinding.root), View.OnClickListener {

    var onRecipeListener: OnRecipeListener? = null

    init {
        this.onRecipeListener = onRecipeListener

        itemView.setOnClickListener(this);
    }


    override fun onClick(view: View?) {
        onRecipeListener?.onRecipeClick(adapterPosition);

    }
}