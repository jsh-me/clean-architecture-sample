package com.jsh.tenqube.data.shop.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "shopList")
data class LocalShopModel constructor(
    @PrimaryKey @ColumnInfo(name ="id") var id: String,
    @ColumnInfo(name = "shopName") var shopName: String,
    @ColumnInfo(name = "shopUrl") var shopUrl: String,
    @ColumnInfo(name = "labels") var labels: String
)
