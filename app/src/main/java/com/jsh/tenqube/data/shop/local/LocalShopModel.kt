package com.jsh.tenqube.data.shop.local

import androidx.room.*
import com.jsh.tenqube.data.label.local.LocalLabelModel

@Entity(tableName = "shopList")
data class LocalShopModel constructor(
    @PrimaryKey @ColumnInfo(name ="id") var id: String,
    @ColumnInfo(name = "shopName") var shopName: String,
    @ColumnInfo(name = "shopUrl") var shopUrl: String,
    @ColumnInfo(name = "labels") var labels: String
)
