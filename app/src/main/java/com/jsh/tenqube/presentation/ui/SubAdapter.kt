package com.jsh.tenqube.presentation.ui

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jsh.tenqube.R
import kotlinx.android.synthetic.main.item_label_list.view.*

class SubAdapter(
    private val label: List<String>
) :
    RecyclerView.Adapter<Holder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val inflatedView =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_label_list, parent, false)
        return Holder(inflatedView)

    }

    override fun getItemCount(): Int = label.size

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(label[position])
    }
}

class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    @SuppressLint("SetTextI18n")
    fun bind(name: String) {
        itemView.label.text = "#$name"
    }
}