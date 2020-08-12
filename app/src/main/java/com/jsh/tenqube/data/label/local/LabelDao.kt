package com.jsh.tenqube.data.label.local

import androidx.room.*
import com.jsh.tenqube.data.label.local.DataLabel.*

@Dao
interface LabelDao{

    @Query("SELECT * FROM label")
    suspend fun getLabels(): List<LocalLabelModel>

    @Query("DELETE FROM label")
    suspend fun deleteAllLabels()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLabel(label: LocalLabelModel)

    @Query("SELECT count(*) FROM label")
    suspend fun isLabelDBEmpty(): Int

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateLabel(label: LocalLabelModel)

}