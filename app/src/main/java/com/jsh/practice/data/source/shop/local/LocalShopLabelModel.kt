package com.jsh.practice.data.source.shop.local

import androidx.room.*

@Entity(tableName = "shopLabel", primaryKeys = ["shopId", "labelId"])
    data class LocalShopLabelModel (
    @ColumnInfo(name = "shopId") val shopId: String,
    @ColumnInfo(name = "labelId") val labelId: String
)
