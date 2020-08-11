package com.jsh.tenqube.data.shop.local

import androidx.room.*
import com.jsh.tenqube.data.shop.local.DataShop.*

@Dao
interface ShopDao{

    @Query("SELECT * FROM shop")
    suspend fun getShops(): List<LocalShopModel>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertShop(shop: LocalShopModel)

    @Query("SELECT * FROM shop WHERE shopId= :shopId")
    suspend fun getShopById(shopId: String): LocalShopModel

    @Query("DELETE FROM shop WHERE shopId= :shopId")
    suspend fun deleteShopById(shopId: String)

    @Query("SELECT count(*) FROM shop")
    suspend fun isShopDBEmpty(): Int

    @Query("DELETE FROM shop")
    suspend fun deleteAllShops()

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateShop(shop: LocalShopModel)



}