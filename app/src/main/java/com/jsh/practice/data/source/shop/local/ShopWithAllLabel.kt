package com.jsh.practice.data.source.shop.local

import androidx.room.*
import com.jsh.practice.data.source.label.local.LocalLabelModel


data class ShopWithAllLabel(
    @Embedded val shop: LocalShopModel,

    @Relation(
        parentColumn = "shopId",
        entityColumn = "labelId",
        associateBy = Junction(LocalShopLabelModel::class)
        )
    val shopLabels: List<LocalLabelModel>
)