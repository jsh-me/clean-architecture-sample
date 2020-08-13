package com.jsh.tenqube.data.source.shop.local

import androidx.room.*

sealed class DataShop {
    @Entity(tableName = "shop")
    data class LocalShopModel(
        @PrimaryKey @ColumnInfo(name = "shopId") var id: String,
        @ColumnInfo(name = "shopName") var shopName: String,
        @ColumnInfo(name = "shopUrl") var shopUrl: String
    ): DataShop()
}