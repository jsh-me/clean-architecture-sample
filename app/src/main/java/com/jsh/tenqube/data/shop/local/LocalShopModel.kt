package com.jsh.tenqube.data.shop.local

import androidx.room.*

/*
@Entity(tableName = "shopList")
data class LocalShopModel constructor(
    @PrimaryKey @ColumnInfo(name = "id") var id: String,
    @ColumnInfo(name = "shopName") var shopName: String,
    @ColumnInfo(name = "shopUrl") var shopUrl: String
//    @ColumnInfo(name = "label") var label: String
)
*/

@Entity(tableName = "shop")
data class LocalShopModel (
    @PrimaryKey @ColumnInfo(name = "id") var id: String,
    @ColumnInfo(name = "shopName") var shopName: String,
    @ColumnInfo(name = "shopUrl") var shopUrl: String
)