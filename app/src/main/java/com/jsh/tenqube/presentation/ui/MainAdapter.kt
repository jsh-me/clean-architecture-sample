package com.jsh.tenqube.presentation.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jsh.tenqube.R
import com.jsh.tenqube.databinding.ItemShopListBinding
import com.jsh.tenqube.databinding.ItemShopListBindingImpl
import com.jsh.tenqube.domain.entity.Shop
import com.jsh.tenqube.presentation.util.toLoadUrl
import com.jsh.tenqube.presentation.util.toValue
import kotlinx.android.synthetic.main.item_shop_list.view.*
import timber.log.Timber

class MainAdapter:
RecyclerView.Adapter<MainAdapter.Holder>() {

    var shop: List<Shop> = listOf()
    var labelMap: Map<String, String> = mutableMapOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemShopListBinding.inflate(inflater, parent, false)
        return Holder(
            binding,
            binding.shopName,
            binding.shopImage,
            binding.subRecycler
        )
    }

    override fun getItemCount(): Int = shop.size

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.shopName.text = shop[position].name
        holder.shopImage.toLoadUrl(shop[position].imgUrl)
        holder.shopImage.clipToOutline = true
        holder.subRecycler.run{
            adapter = SubAdapter(shop[position].labelIds.toValue(labelMap))
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        }
        Timber.e("label: ${labelMap.size} and shop: ${shop.size}")
    }

    inner class Holder(
        binding: ItemShopListBinding,
        val shopName: TextView,
        val shopImage: ImageView,
        val subRecycler: RecyclerView
    ) : RecyclerView.ViewHolder(binding.root)
}