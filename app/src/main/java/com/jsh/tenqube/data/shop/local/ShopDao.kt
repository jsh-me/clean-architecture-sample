package com.jsh.tenqube.data.shop.local

import androidx.room.*
import com.jsh.tenqube.data.db.ShopAndAllLabels
import com.jsh.tenqube.data.label.local.LocalLabelModel

@Dao
interface ShopDao{

    @Query("SELECT * FROM shopList")
    suspend fun getShops(): List<LocalShopModel>

    @Transaction
    @Query("SELECT * FROM shopList")
    suspend fun getShopAndAllLabels(): List<ShopAndAllLabels>

    @Insert
    suspend fun insertShop(shop: LocalShopModel)

    @Query("SELECT * FROM shopList WHERE id= :shopId")
    suspend fun getShopById(shopId: String): LocalShopModel

    @Query("DELETE FROM shopList WHERE id= :shopId")
    suspend fun deleteShopById(shopId: String)

    @Query("SELECT count(*) FROM shopList")
    suspend fun isShopDBEmpty(): Int

    @Query("DELETE FROM shopList")
    suspend fun deleteAllShops()

    @Update
    suspend fun saveShop(shop: LocalShopModel)

}