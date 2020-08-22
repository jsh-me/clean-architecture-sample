package com.jsh.practice.presentation.binding

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.jsh.practice.presentation.entity.PresenterShop
import com.jsh.practice.presentation.ui.first.MainAdapter
import com.jsh.practice.presentation.util.toLoadUrl
import timber.log.Timber

@BindingAdapter("setItems")
fun bindShopRecyclerView(recyclerView: RecyclerView, shopAndLabel: List<PresenterShop>?) {
    shopAndLabel?.let {
        (recyclerView.adapter as MainAdapter).submitList(it)
        Timber.e("first binding end! size: ${it.size}")
        recyclerView.adapter?.notifyDataSetChanged()
    }
}

@BindingAdapter("bindImage")
fun bindImageView(imageView: ImageView, src: String?) {
    src?.let {
        imageView.toLoadUrl(it)
    }
}
