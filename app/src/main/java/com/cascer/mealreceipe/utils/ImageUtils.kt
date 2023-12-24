package com.cascer.mealreceipe.utils

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.cascer.mealreceipe.R

object ImageUtils {
    fun ImageView.load(context: Context, url: String) {
        Glide.with(context)
            .load(url.trim())
            .error(R.drawable.baseline_broken_image_24)
            .into(this)
    }

    fun ImageView.loadCircle(context: Context, url: String) {
        Glide.with(context)
            .load(url.trim())
            .circleCrop()
            .into(this)
    }
}