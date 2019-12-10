package com.broadcast.recipeslistapp.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.broadcast.recipeslistapp.`interface`.OnRecipeListener
import com.broadcast.recipeslistapp.*
import android.widget.TextView
import de.hdodenhof.circleimageview.CircleImageView


class CategoryViewHolder(itemView: View, onRecipeListener: OnRecipeListener) :
    RecyclerView.ViewHolder(itemView), View.OnClickListener {
    var categoryImage: CircleImageView? = null
    var categoryTitle: TextView? = null
    var listener: OnRecipeListener? = null
    init {
        categoryImage = itemView.findViewById(R.id.category_image);
        categoryTitle = itemView.findViewById(R.id.category_title);
        listener = onRecipeListener;
        itemView.setOnClickListener(this);
    }

    override fun onClick(view: View?) {
        listener?.onCategoryClick("${categoryTitle!!.text}")

    }
}