package com.jsh.tenqube.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction

@Dao
interface ShopLabelDao{

/*    @Insert
    suspend fun insertShopLabel(shopLabel: LocalShopLabelModel)

    @Query("SELECT * FROM shopAndLabelList")
    suspend fun getShopAndLabels(): List<LocalShopLabelModel>

    @Query("DELETE FROM shopAndLabelList")
    suspend fun deleteAllShopAndLabels()

    @Query("SELECT count(*) FROM shopAndLabelList")
    suspend fun isShopAndLabelEmpty(): Int

//    @Transaction
//    @Query("SELECT * FROM shopList")
//    suspend fun getShopWithLabelList(): List<ShopLabelWithLabelList>

    @Transaction
    @Query("SELECT * FROM shopList")
    suspend fun getShopWithAllLabelList(): List<ShopWithAllLabelList>*/


    @Query("SELECT * FROM shop")
    fun getShop(): List<LocalShopAndLabels>
}

