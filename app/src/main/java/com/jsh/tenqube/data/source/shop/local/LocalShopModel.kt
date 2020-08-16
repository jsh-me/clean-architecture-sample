package com.jsh.tenqube.data.source.shop.local

import androidx.room.*

@Entity(tableName = "shop")
data class LocalShopModel(
    @PrimaryKey @ColumnInfo(name = "shopId") val id: String,
    @ColumnInfo(name = "shopName") val shopName: String,
    @ColumnInfo(name = "shopUrl") val shopUrl: String
)