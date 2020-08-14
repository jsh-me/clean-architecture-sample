package com.jsh.tenqube.data.source.shop.local

import androidx.room.*
import com.jsh.tenqube.data.source.shop.local.DataShop.*
import com.jsh.tenqube.data.source.shop.local.DataShopLabel.LocalShopLabelModel

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

    @Transaction
    @Query("SELECT * FROM shop")
    fun getShopWithAllLabel(): List<ShopWithAllLabel>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertShopWithLabel(shopLabel: LocalShopLabelModel)

    @Query("DELETE FROM shopLabel")
    fun deleteAllShopLabel()

    @Insert
    fun updateShopLabels(shopLabel: LocalShopLabelModel)

}