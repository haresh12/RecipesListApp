package com.broadcast.recipeslistapp.util

import android.graphics.drawable.Drawable
import android.net.Uri
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

@BindingAdapter("imageUrl")
fun loadImage(view: ImageView, imageUrl: String) {
////    Picasso.get()
////        .load(imageUrl)
////        .placeholder(view.context.resources.getDrawable(com.topscorerstudent.app.R.drawable.ic_cyan_hexa))
////        .into(view)
    val path = Uri.parse(
        "android.resource://com.broadcast.recipeslistapp/drawable/" + imageUrl
    )
    Glide.with(view.context).load(path).into(view);

}

@BindingAdapter("drawable")
fun setImageDrawable(view: ImageView, drawable: Drawable) {
    view.setImageDrawable(drawable)
}

@BindingAdapter("image")
fun loadRecipeImage(view: ImageView, url: String?) {
    val option = RequestOptions().centerCrop()
        .error(com.broadcast.recipeslistapp.R.drawable.ic_launcher_background)
    if (!url.isNullOrEmpty()) {
        Glide.with(view.context)
            .setDefaultRequestOptions(option).load(url).into(view)
    }
}