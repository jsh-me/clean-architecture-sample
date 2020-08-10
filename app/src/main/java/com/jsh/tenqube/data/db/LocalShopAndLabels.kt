package com.jsh.tenqube.data.db

import androidx.room.*
import com.jsh.tenqube.data.label.local.LocalLabelModel
import com.jsh.tenqube.data.shop.local.LocalShopModel

//@Entity(tableName = "shopAndLabelList",
//    indices = arrayOf(Index(value = ["labelId"],
//        unique = true)),
//    foreignKeys = [
//    ForeignKey(
//    entity = LocalShopModel::class,
//    parentColumns = arrayOf("id"),
//    childColumns = arrayOf("shopId"),
//    onDelete = ForeignKey.NO_ACTION
//)]
//)
//
//data class LocalShopLabelModel constructor(
//    @PrimaryKey(autoGenerate = true) var id: Int? = 0,
//    @ColumnInfo(name = "shopId") var shopId: String,
//    @ColumnInfo(name = "labelId") var labelId: String
//)


data class LocalShopAndLabels (
    @Embedded val shop: LocalShopModel,
    @Relation(parentColumn = "id", entityColumn = "shop_id")
    val labels: List<LocalLabelModel>
)