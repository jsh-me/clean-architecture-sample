package com.jsh.tenqube.presentation.ui.first

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.jsh.tenqube.BR
import com.jsh.tenqube.databinding.ItemShopListBinding
import com.jsh.tenqube.presentation.entity.PresenterShopLabel.*
import com.jsh.tenqube.presentation.ui.first.MainAdapter.*
import com.jsh.tenqube.presentation.ui.second.SecondActivity
import timber.log.Timber


class MainAdapter(private val viewModel: FirstViewModel):
    ListAdapter<PresenterShopLabelList, FirstViewHolder>(ShopDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FirstViewHolder {
        return FirstViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: FirstViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(viewModel, item)
    }

    override fun getItemCount(): Int {
        Timber.e("item count: ${super.getItemCount()}")
        return super.getItemCount()
    }

    class FirstViewHolder private constructor(val binding: ItemShopListBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(viewModel: FirstViewModel, item: PresenterShopLabelList) {

            var labelNameList = ""

            item.shopLabels.map {
                labelNameList += "#${it.name} "
            }

            binding.shopImage.clipToOutline = true
            binding.viewModel = viewModel
            binding.shop = item.shop
            binding.label = labelNameList
        }

        companion object {
            fun from(parent: ViewGroup): FirstViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemShopListBinding.inflate(layoutInflater, parent, false)

                return FirstViewHolder(binding)
            }
        }
    }
}

/**
 * Callback for calculating the diff between two non-null items in a list.
 *
 * Used by ListAdapter to calculate the minimum number of changes between and old list and a new
 * list that's been passed to `submitList`.
 */
class ShopDiffCallback : DiffUtil.ItemCallback<PresenterShopLabelList>() {
    override fun areItemsTheSame(oldItem: PresenterShopLabelList, newItem: PresenterShopLabelList): Boolean {
        return (oldItem.shop.shopId == newItem.shop.shopId) && (oldItem.shop.shopName == newItem.shop.shopName) && (oldItem.shop.shopUrl == oldItem.shop.shopUrl)
    }

    override fun areContentsTheSame(oldItem: PresenterShopLabelList, newItem: PresenterShopLabelList): Boolean {
        return oldItem == newItem
    }
}

//
//class MainAdapter(
//):
//RecyclerView.Adapter<MainAdapter.Holder>() {
//
//
//    private var shopLabel: List<PresenterShopLabelList> = listOf()
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
//        val inflater = LayoutInflater.from(parent.context)
//        val binding = ItemShopListBinding.inflate(inflater, parent, false)
//
//        return Holder(
//            binding,
//            binding.shopName,
//            binding.shopImage,
//            binding.labelName,
//            binding.mainConstraint
//        )
//    }
//
//    override fun getItemCount(): Int = shopLabel.size
//
//    override fun onBindViewHolder(holder: Holder, position: Int) {
//        var labelNameList = ""
//
//        shopLabel[position].shopLabels.map {
//            labelNameList += "#${it.name} "
//        }
//
//        holder.bind(shopLabel[position], labelNameList)
//
//        holder.shopImage.clipToOutline = true
//
//        holder.constraint.setOnClickListener {
//        }
//
//    }
//
//    fun setList(list: List<PresenterShopLabelList>) {
//        shopLabel = list
//    }
//
//    inner class Holder(
//        val binding: ItemShopListBinding,
//        val shopName: TextView,
//        val shopImage: ImageView,
//        val labelName: TextView,
//        val constraint: ConstraintLayout
//    ) : RecyclerView.ViewHolder(binding.root){
//
//        fun bind(shopList: PresenterShopLabelList, labelList: String){
//
//            binding.setVariable(BR.shop, shopList.shop)
//            binding.setVariable(BR.label, labelList)
//        }
//
//    }
//}
////
///**
// * Callback for calculating the diff between two non-null items in a list.
// *
// * Used by ListAdapter to calculate the minimum number of changes between and old list and a new
// * list that's been passed to `submitList`.
// */
//class ShopDiffCallback : DiffUtil.ItemCallback<PresenterShop>() {
//    override fun areItemsTheSame(oldItem: PresenterShop, newItem: PresenterShop): Boolean {
//        return (oldItem.shopName == newItem.shopName) && (oldItem.shopId == newItem.shopId) && (oldItem.shopUrl == oldItem.shopUrl)
//    }
//
//    override fun areContentsTheSame(oldItem: PresenterShop, newItem: PresenterShop): Boolean {
//        return oldItem == newItem
//    }
//}

