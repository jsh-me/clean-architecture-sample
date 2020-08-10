package com.jsh.tenqube.data.db

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.jsh.tenqube.data.label.local.LocalLabelModel
import com.jsh.tenqube.data.shop.local.LocalShopModel
//
////Shop 과 ShopLabel 을 이어주는 data class
//data class ShopLabelWithLabelList (
//
//    @Embedded
//    var shop: LocalShopLabelModel,
//
//    @Relation(
//        parentColumn = "labelId",
//        entityColumn = "id", //labelid
//        associateBy = Junction(ShopLabelAndLabelCrossRef::class)
//    )
//    val labels: List<LocalLabelModel>
//)