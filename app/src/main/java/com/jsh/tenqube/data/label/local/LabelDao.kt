package com.jsh.tenqube.data.label.local

import androidx.room.*

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

//    @Query("SELECT * FROM label INNER JOIN shopAndLabelList ON shopAndLabelList.labelId = labelList.id INNER JOIN shopList ON shopList.id = shopAndLabelList.shopId WHERE shopList.shopName LIKE :shopName")
//    suspend fun findLabelsByShopName(shopName: String): List<LocalLabelModel>
//
//    @Query("SELECT * FROM label INNER JOIN shopAndLabelList ON shopAndLabelList.labelId = labelList.id WHERE shopAndLabelList.shopId LIKE :shopId")
//    suspend fun findLabelsByShopId(shopId: String): List<LocalLabelModel>


}