package com.jsh.practice.data.source.label.local

import androidx.room.*

@Dao
interface LabelDao{

    @Query("SELECT * FROM label")
    suspend fun getLabels(): List<LocalLabelModel>

    @Transaction
    @Query("DELETE FROM label")
    suspend fun deleteAllLabels()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLabel(label: LocalLabelModel)

    @Query("SELECT count(*) FROM label")
    suspend fun isLabelDBEmpty(): Int

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateLabel(label: LocalLabelModel)

    @Query("SELECT * FROM label WHERE labelID=:labelId")
    suspend fun getLabelById(labelId: String): LocalLabelModel

}