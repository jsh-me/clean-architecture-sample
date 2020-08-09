package com.jsh.tenqube.data.shop.local

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Update
import com.jsh.tenqube.data.label.local.LocalLabelModel

@Dao
interface ShopDao{

    @Query("SELECT * FROM shopList")
    suspend fun getShops(): List<LocalShopModel>

    @Query("SELECT * FROM shopList WHERE id= :shopId")
    suspend fun getShopById(shopId: String): LocalShopModel

    @Query("DELETE FROM shopList WHERE id= :shopId")
    suspend fun deleteShopById(shopId: String)

//    @Query("SELECT * FROM labelList")
//    suspend fun getLabels(): List<LocalLabelModel>

    @Query("DELETE FROM shopList")
    suspend fun deleteAllShops()

//    @Query("DELETE FROM labelList")
//    suspend fun deleteAllLabels()

    @Update
    suspend fun saveShop(shop: LocalShopModel)

}