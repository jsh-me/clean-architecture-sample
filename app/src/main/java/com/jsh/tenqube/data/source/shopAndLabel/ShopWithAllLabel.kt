package com.jsh.tenqube.data.source.shopAndLabel

import androidx.room.*
import com.jsh.tenqube.data.source.label.local.DataLabel.*
import com.jsh.tenqube.data.source.shop.local.DataShop.*
import com.jsh.tenqube.data.source.shopAndLabel.local.DataShopLocal.*


data class ShopWithAllLabel(
    @Embedded val shop: LocalShopModel,

    @Relation(
        parentColumn = "shopId",
        entityColumn = "labelId",
        associateBy = Junction(LocalShopLabelModel::class)
        )
    val shopLabels: List<LocalLabelModel>

)