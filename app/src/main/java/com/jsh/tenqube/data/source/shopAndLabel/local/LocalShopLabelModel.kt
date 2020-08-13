package com.jsh.tenqube.data.source.shopAndLabel.local

import androidx.room.*

sealed class DataShopLocal {
    @Entity(tableName = "shopLabel", primaryKeys = ["shopId", "labelId"])
    data class LocalShopLabelModel(
        @ColumnInfo(name = "shopId") var shopId: String,
        @ColumnInfo(name = "labelId") var labelId: String
    ): DataShopLocal()
}