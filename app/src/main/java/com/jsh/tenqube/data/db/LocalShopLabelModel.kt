package com.jsh.tenqube.data.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.jsh.tenqube.data.shop.local.LocalShopModel

@Entity(tableName="shopLabel",
    foreignKeys = [
    ForeignKey(
    entity = LocalShopModel::class,
    parentColumns = arrayOf("id"),
    childColumns = arrayOf("shopId"),
    onDelete = ForeignKey.NO_ACTION
)])
data class LocalShopLabelModel(
    @PrimaryKey(autoGenerate = true) var num: Int?,
    @ColumnInfo(name="shopId") var shopId: String,
    @ColumnInfo(name="labelId") var labelId: String
)