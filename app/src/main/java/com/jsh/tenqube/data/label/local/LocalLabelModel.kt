package com.jsh.tenqube.data.label.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "labelList")
data class LocalLabelModel constructor(
    @PrimaryKey @ColumnInfo(name ="id") var id: String,
    @ColumnInfo(name = "name") var name: String
)