package com.broadcast.recipeslistapp

import androidx.appcompat.app.AppCompatActivity
import android.view.View.*
import android.widget.FrameLayout
import androidx.constraintlayout.widget.ConstraintLayout
import kotlinx.android.synthetic.main.activity_base.*


abstract class BaseActivity : AppCompatActivity() {

    override fun setContentView(layoutResID: Int) {
        val constraintLayout =
            layoutInflater.inflate(R.layout.activity_base, null) as ConstraintLayout
        val frameLayout = constraintLayout.findViewById<FrameLayout>(R.id.activity_content)
        layoutInflater.inflate(layoutResID, frameLayout, true)
        super.setContentView(constraintLayout);

    }

    fun showProgressBar(isVisible: Boolean) = if (isVisible) {
        progress_bar.visibility = VISIBLE
    } else {
        progress_bar.visibility = INVISIBLE
    }

}
