package com.jsh.tenqube.presentation.ui

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jsh.tenqube.R
import com.jsh.tenqube.databinding.ItemShopListBinding
import com.jsh.tenqube.domain.entity.Label
import com.jsh.tenqube.domain.entity.Shop
import com.jsh.tenqube.presentation.util.toLoadUrl
import com.jsh.tenqube.presentation.util.toValue
import kotlinx.android.synthetic.main.item_shop_list.view.*
import timber.log.Timber

class MainAdapter:
RecyclerView.Adapter<MainAdapter.Holder>() {

    var shop: List<Shop> = listOf()
    var labelMap: List<List<Label>> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemShopListBinding.inflate(inflater, parent, false)
        return Holder(
            binding,
            binding.shopName,
            binding.shopImage,
            binding.subRecycler,
            binding.mainConstraint
        )
    }

    override fun getItemCount(): Int = shop.size

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.shopName.text = shop[position].name
        holder.shopImage.toLoadUrl(shop[position].imgUrl)
        holder.shopImage.clipToOutline = true
        holder.subRecycler.run{
            adapter = SubAdapter(labelMap[position])
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        }
        holder.constraint.setOnClickListener {
            val intent = Intent(it.context, SubActivity::class.java).apply {
                putExtra("ShopId", shop[position].id)
                putExtra("ShopName", shop[position].name)
                putExtra("ShopUrl", shop[position].imgUrl)
            }
            it.context.startActivity(intent)
            Toast.makeText(it.context, shop[position].id, Toast.LENGTH_LONG).show()
        }


        Timber.e("label: ${labelMap.size} and shop: ${shop.size}")
    }

    inner class Holder(
        binding: ItemShopListBinding,
        val shopName: TextView,
        val shopImage: ImageView,
        val subRecycler: RecyclerView,
        val constraint: ConstraintLayout
    ) : RecyclerView.ViewHolder(binding.root)
}