package com.jsh.practice.data.source.label.local

import androidx.room.*

@Entity(tableName = "label")
data class LocalLabelModel(
     @PrimaryKey @ColumnInfo(name = "labelId") val id: String,
     @ColumnInfo(name = "name") val name: String
)