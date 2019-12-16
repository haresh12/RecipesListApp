package com.broadcast.recipeslistapp.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.broadcast.recipeslistapp.com.broadcast.recipeslistapp.adapter.OnRecipeListener
import com.broadcast.recipeslistapp.databinding.LayoutCategoryListItemBinding
import kotlinx.android.synthetic.main.layout_category_list_item.view.*


class CategoryViewHolder(
    val layoutCategoryListItemBinding: LayoutCategoryListItemBinding,
    onRecipeListener: OnRecipeListener
) :
    RecyclerView.ViewHolder(layoutCategoryListItemBinding.root), View.OnClickListener {

    var listener: OnRecipeListener? = null

    init {
        listener = onRecipeListener;
        itemView.setOnClickListener(this);
    }

    override fun onClick(view: View?) {
        listener?.onCategoryClick("${itemView.category_title.text}")

    }
}