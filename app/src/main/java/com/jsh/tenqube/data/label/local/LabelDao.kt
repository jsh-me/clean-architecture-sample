package com.jsh.tenqube.data.label.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface LabelDao{

    @Query("SELECT * FROM labelList")
    suspend fun getLabels(): List<LocalLabelModel>

    @Query("DELETE FROM labelList")
    suspend fun deleteAllLabels()

    @Insert
    suspend fun insertLabel(label: LocalLabelModel)

    @Query("SELECT count(*) FROM labelList")
    suspend fun isLabelDBEmpty(): Int
}