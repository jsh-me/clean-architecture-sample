package com.jsh.tenqube.presentation.binding

import android.text.Layout
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jsh.tenqube.presentation.entity.PresenterShopLabel.*
import com.jsh.tenqube.presentation.ui.first.FirstViewModel
import com.jsh.tenqube.presentation.ui.first.MainAdapter
import com.jsh.tenqube.presentation.util.toLoadUrl
import timber.log.Timber

@BindingAdapter("app:items")
fun bindShopRecyclerView(recyclerView: RecyclerView, shopAndLabel: List<PresenterShopLabelList>?){
    shopAndLabel?.let{
        (recyclerView.adapter as MainAdapter).submitList(it)
        Timber.e("first binding end! size: ${it.size}")
        recyclerView.adapter?.notifyDataSetChanged()
    }
}

@BindingAdapter("bindImage")
fun bindImageView(imageView: ImageView, src: String){
    imageView.toLoadUrl(src)
}
