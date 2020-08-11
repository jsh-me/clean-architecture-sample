package com.jsh.tenqube.data.label.local

import androidx.room.*

sealed class DataLabel {
     @Entity(tableName = "label")
     data class LocalLabelModel(
          @PrimaryKey @ColumnInfo(name = "labelId") var id: String,
          @ColumnInfo(name = "name") var name: String
     ): DataLabel()
}