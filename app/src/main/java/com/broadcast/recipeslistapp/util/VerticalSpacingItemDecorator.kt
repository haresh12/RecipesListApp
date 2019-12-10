package com.broadcast.recipeslistapp.util

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class VerticalSpacingItemDecorator(verticalSpaceHeight: Int) : RecyclerView.ItemDecoration() {
    private var verticalSpaceHeight: Int = 0
    init {
        this.verticalSpaceHeight=verticalSpaceHeight
    }

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
         outRect.top=verticalSpaceHeight
    }

}