package com.jsh.tenqube.presentation.binding

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jsh.tenqube.presentation.entity.PresenterShopLabel.*
import com.jsh.tenqube.presentation.ui.first.MainAdapter
import com.jsh.tenqube.presentation.ui.second.SecondAdapter
import com.jsh.tenqube.presentation.util.toLoadUrl
import timber.log.Timber

@BindingAdapter("bindShopAndLabelList")
fun bindShopRecyclerView(recyclerView: RecyclerView, shopAndLabel: List<PresenterShopLabelList>?){
    if(shopAndLabel != null) {
        val lm = LinearLayoutManager(recyclerView.context)
        val adapter = MainAdapter()
        recyclerView.layoutManager = lm
        recyclerView.adapter = adapter

        (recyclerView.adapter as MainAdapter).run {
            setShopList(shopAndLabel.map{ it.shop })
            setLabelMap(shopAndLabel.map{ it.shopLabels })
        }
    }
    recyclerView.adapter?.notifyDataSetChanged()
    Timber.e("first binding end!")
}

@BindingAdapter("bindImage")
fun bindImageView(imageView: ImageView, src: String){
    imageView.toLoadUrl(src)
}

@BindingAdapter("bindLabelList")
fun bindShopRecyclerView(recyclerView: RecyclerView, Labels: ArrayList<String>?){
    if(Labels != null) {
        val lm = LinearLayoutManager(recyclerView.context, LinearLayoutManager.HORIZONTAL, false)
        val adapter = SecondAdapter()
        recyclerView.layoutManager = lm
        recyclerView.adapter = adapter

        (recyclerView.adapter as SecondAdapter).run {
            setLabelMap(Labels)
        }
    }
    recyclerView.adapter?.notifyDataSetChanged()
}