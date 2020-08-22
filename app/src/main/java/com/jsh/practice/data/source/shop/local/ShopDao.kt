package com.jsh.practice.data.source.shop.local

import androidx.room.*

@Dao
interface ShopDao{

    @Query("SELECT * FROM shop")
    suspend fun getShops(): List<LocalShopModel>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertShop(shop: LocalShopModel)

    @Query("SELECT * FROM shop WHERE shopId= :shopId")
    suspend fun getShopById(shopId: String): LocalShopModel

    @Transaction
    @Query("DELETE FROM shop WHERE shopId= :shopId")
    suspend fun deleteShopById(shopId: String)

    @Query("SELECT count(*) FROM shop")
    suspend fun isShopDBEmpty(): Int

    @Transaction
    @Query("DELETE FROM shop")
    suspend fun deleteAllShops()

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateShop(shop: LocalShopModel)

    @Transaction
    @Query("SELECT * FROM shop")
    fun getShopWithAllLabel(): List<ShopWithAllLabel>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertShopWithLabel(shopLabel: LocalShopLabelModel)

    @Transaction
    @Query("DELETE FROM shopLabel")
    fun deleteAllShopLabel()
}