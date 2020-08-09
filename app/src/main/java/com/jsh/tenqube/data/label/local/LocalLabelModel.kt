package com.jsh.tenqube.data.label.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.jsh.tenqube.data.shop.local.LocalShopModel


@Entity(tableName = "labelList")
data class LocalLabelModel constructor(
    @ColumnInfo(name ="id") var id: String,
    @PrimaryKey @ColumnInfo(name = "name") var name: String
)