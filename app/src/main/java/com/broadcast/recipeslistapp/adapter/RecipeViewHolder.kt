package com.broadcast.recipeslistapp.adapter

import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.broadcast.recipeslistapp.`interface`.OnRecipeListener
import com.broadcast.recipeslistapp.*
import androidx.appcompat.widget.AppCompatImageView
import android.widget.TextView


class RecipeViewHolder(itemView: View, onRecipeListener: OnRecipeListener) :
    RecyclerView.ViewHolder(itemView), View.OnClickListener {
    var title: TextView? = null
    var publisher: TextView? = null
    var socialScore: TextView? = null
    var image: ImageView? = null
    var onRecipeListener: OnRecipeListener? = null

    init {
        this.onRecipeListener = onRecipeListener
        title = itemView.findViewById(R.id.recipe_title)
        publisher = itemView.findViewById(R.id.recipe_publisher)
        socialScore = itemView.findViewById(R.id.recipe_social_score)
        image = itemView.findViewById(R.id.recipe_image)
        itemView.setOnClickListener(this);
    }


    override fun onClick(view: View?) {
        onRecipeListener?.onRecipeClick(adapterPosition);

    }
}