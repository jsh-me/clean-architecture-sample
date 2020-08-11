package com.jsh.tenqube.data.label.local

import androidx.room.*

@Dao
interface LabelDao{

    @Query("SELECT * FROM label")
    suspend fun getLabels(): List<DataLabel.LocalLabelModel>

    @Query("DELETE FROM label")
    suspend fun deleteAllLabels()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLabel(label: DataLabel.LocalLabelModel)

    @Query("SELECT count(*) FROM label")
    suspend fun isLabelDBEmpty(): Int
}