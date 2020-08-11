package com.jsh.tenqube.data.label.local

import androidx.room.*
import com.jsh.tenqube.data.db.LocalShopLabelModel


//@Entity(tableName = "labelList", foreignKeys = [
//     ForeignKey(
//          entity = LocalShopLabelModel::class,
//          parentColumns = arrayOf("labelId"),
//          childColumns = arrayOf("id"),
//          onDelete = ForeignKey.NO_ACTION
//     )])
//
//data class LocalLabelModel constructor(
//     @PrimaryKey @ColumnInfo(name ="id") var id: String,
//     @ColumnInfo(name = "name") var name: String
//)

@Entity(
     tableName = "label",
     foreignKeys = [ForeignKey(
          entity = LocalShopLabelModel::class,
          parentColumns = arrayOf("labelId"),
          childColumns = arrayOf("id"),
          onDelete = ForeignKey.NO_ACTION
     )])
data class LocalLabelModel (
     @PrimaryKey @ColumnInfo(name ="id") var id: String,
     //@ColumnInfo(name = "shop_id") var shopId: String,
     @ColumnInfo(name = "name") var name: String
)