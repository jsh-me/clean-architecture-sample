package com.jsh.tenqube.presentation.ui.second

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.jsh.tenqube.databinding.ItemLabelListBinding


class SecondAdapter:
    RecyclerView.Adapter<SecondAdapter.Holder>() {

    private var labels: ArrayList<String> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemLabelListBinding.inflate(inflater, parent, false)
        return Holder(
            binding,
            binding.label
        )
    }

    override fun getItemCount(): Int = labels.size

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.label.text = "# ${labels[position]} "
    }

    fun setLabelMap(label: ArrayList<String>){
        labels = label
    }

    inner class Holder(
        binding: ItemLabelListBinding,
        val label: TextView
    ) : RecyclerView.ViewHolder(binding.root)
}