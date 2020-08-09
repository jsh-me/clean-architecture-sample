package com.jsh.tenqube.presentation.binding

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jsh.tenqube.domain.entity.Shop
import com.jsh.tenqube.presentation.ui.MainAdapter
import timber.log.Timber

@BindingAdapter("bindShopList", "bindLabelList")
fun bindShopRecyclerView(recyclerView: RecyclerView, shopList: List<Shop>?, labelList: Map<String, String>?){
    if(shopList != null && labelList != null) {
        val lm = LinearLayoutManager(recyclerView.context)
        val adapter = MainAdapter()
        recyclerView.layoutManager = lm
        recyclerView.adapter = adapter

        (recyclerView.adapter as MainAdapter).run {
            shop = shopList
            labelMap = labelList
            Timber.e("label: ${labelMap.size} and shop: ${shop.size}")
        }
    }
        recyclerView.adapter?.notifyDataSetChanged()
    Timber.e("first binding end!")
}