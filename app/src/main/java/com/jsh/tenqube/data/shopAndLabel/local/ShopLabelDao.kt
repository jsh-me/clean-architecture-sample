package com.jsh.tenqube.data.shopAndLabel.local

import androidx.room.*
import com.jsh.tenqube.data.shopAndLabel.local.DataShopLocal.LocalShopLabelModel
import com.jsh.tenqube.data.shopAndLabel.ShopWithAllLabel

@Dao
interface ShopLabelDao{
    @Transaction
    @Query("SELECT * FROM shop")
    fun getShopWithAllLabel(): List<ShopWithAllLabel>

    @Insert
    fun insertShopWithLabel(shopLabel: LocalShopLabelModel)

    @Query("DELETE FROM shopLabel")
    fun deleteAllShopLabel()

    @Insert
    fun updateShopLabels(shopLabel: LocalShopLabelModel)

}