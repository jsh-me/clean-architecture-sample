package com.jsh.tenqube.data.shop.local

import androidx.room.*

@Dao
interface ShopDao{

    @Query("SELECT * FROM shop")
    suspend fun getShops(): List<LocalShopModel>

//    @Transaction
//    @Query("SELECT * FROM shopList")
//    suspend fun getShopAndAllLabels(): List<ShopToShopAndLabelList>

    @Insert
    suspend fun insertShop(shop: LocalShopModel)

    @Query("SELECT * FROM shop WHERE id= :shopId")
    suspend fun getShopById(shopId: String): LocalShopModel

    @Query("DELETE FROM shop WHERE id= :shopId")
    suspend fun deleteShopById(shopId: String)

    @Query("SELECT count(*) FROM shop")
    suspend fun isShopDBEmpty(): Int

    @Query("DELETE FROM shop")
    suspend fun deleteAllShops()

    @Update
    suspend fun saveShop(shop: LocalShopModel)

}