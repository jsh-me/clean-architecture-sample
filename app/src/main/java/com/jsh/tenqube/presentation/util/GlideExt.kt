package com.jsh.tenqube.presentation.util

import android.widget.ImageView
import com.bumptech.glide.Glide

fun ImageView.toLoadUrl(url: String){
    Glide.with(this)
        .load(url)
        .centerCrop()
        .into(this)
}