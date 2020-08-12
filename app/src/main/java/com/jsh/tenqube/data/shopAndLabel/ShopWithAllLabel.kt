package com.jsh.tenqube.data.shopAndLabel

import androidx.room.*
import com.jsh.tenqube.data.label.local.DataLabel.*
import com.jsh.tenqube.data.shop.local.DataShop.*
import com.jsh.tenqube.data.shopAndLabel.local.DataShopLocal.*


data class ShopWithAllLabel(
    @Embedded val shop: LocalShopModel,

    @Relation(
        parentColumn = "shopId",
        entityColumn = "labelId",
        associateBy = Junction(LocalShopLabelModel::class)
        )
    val shopLabels: List<LocalLabelModel>

)