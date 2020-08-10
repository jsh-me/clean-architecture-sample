package com.jsh.tenqube.data.db

import androidx.room.Embedded
import androidx.room.Relation
import com.jsh.tenqube.data.shop.local.LocalShopModel
//
//data class ShopWithAllLabelList (
//    @Embedded
//    val shop: LocalShopModel,
//
//    @Relation(
//        entity = LocalShopAndLabels::class,
//        parentColumn = "id",//shopid
//        entityColumn = "shopId"
//    )
//    val shopLabelWithLabelList: List<ShopLabelWithLabelList>
//)