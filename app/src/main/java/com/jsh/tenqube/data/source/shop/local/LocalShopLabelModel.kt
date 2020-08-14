package com.jsh.tenqube.data.source.shop.local

import androidx.room.*

sealed class DataShopLabel {
    @Entity(tableName = "shopLabel", primaryKeys = ["shopId", "labelId"])
    data class LocalShopLabelModel(
        @ColumnInfo(name = "shopId") var shopId: String,
        @ColumnInfo(name = "labelId") var labelId: String
    ): DataShopLabel()
}